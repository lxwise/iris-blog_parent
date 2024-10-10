package com.iris.blog.utils;

import cn.hutool.core.util.RandomUtil;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.domain.dto.CaptchaDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CaptchaUtil {


    private static StringRedisTemplate stringRedisTemplate;
    @Resource
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        CaptchaUtil.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 拼图验证码允许偏差值
     **/
    private final static Integer DEVIATION_VALUE = 3;
    /**
     * 网络图片地址
     **/
    private final static String NET_IMG_URL = "https://loyer.wang/view/ftp/wallpaper/%s.jpg";

    /**
     * 本地图片地址
     */
    private static final String CAPTCHA_IMG_PATH = "/captcha/%d.jpg";


    /*********************************获取验证码******************************************************/

    /**
     * 获取验证码拼图（生成的抠图和带抠图阴影的大图及抠图坐标）
     **/
    public CaptchaDTO getCaptcha(CaptchaDTO captcha) {
        //参数校验
        checkCaptcha(captcha);
        //获取画布的宽高
        int canvasWidth = captcha.getCanvasWidth();
        int canvasHeight = captcha.getCanvasHeight();
        //获取阻塞块的宽高/半径
        int blockWidth = captcha.getBlockWidth();
        int blockHeight = captcha.getBlockHeight();
        int blockRadius = captcha.getBlockRadius();
        //获取资源图
        BufferedImage canvasImage = getBufferedImage();
        //调整原图到指定大小
        canvasImage = imageResize(canvasImage, canvasWidth, canvasHeight);
        //随机生成阻塞块坐标
        int blockX = getNonceByRange(blockWidth, canvasWidth - blockWidth - 10);
        int blockY = getNonceByRange(10, canvasHeight - blockHeight + 1);
        //阻塞块
        BufferedImage blockImage = new BufferedImage(blockWidth, blockHeight, BufferedImage.TYPE_4BYTE_ABGR);
        //新建的图像根据轮廓图颜色赋值，源图生成遮罩
        cutByTemplate(canvasImage, blockImage, blockWidth, blockHeight, blockRadius, blockX, blockY);
        // 移动横坐标
        String randomStr = UUID.randomUUID().toString().replaceAll("-", "");
        // 缓存
        saveImageCode(randomStr,String.valueOf(blockX));
        //设置返回参数
        captcha.setRandomStr(randomStr);
        captcha.setBlockY(blockY);
        captcha.setBlockBase64(toBase64(blockImage, "png"));
        captcha.setCanvasBase64(toBase64(canvasImage, "png"));
        return captcha;
    }


    /**
     * 校验验证码
     * @param randomStr
     * @param blockX
     * @return boolean
     **/
    public static void checkImageCode(String randomStr, String blockX) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = RedisKeyConstant.USER_LOGIN_CAPTCHA + randomStr;
        String text = ops.get(key);
        Optional.ofNullable(text).orElseThrow(() -> new BusinessException(ResultCode.CAPTCHA_CODE_ERROR));
        // 根据移动距离判断验证是否成功
        if (Math.abs(Integer.parseInt(text) - Integer.parseInt(blockX)) > DEVIATION_VALUE) {
            throw new BusinessException(ResultCode.CAPTCHA_CODE_VERIFY_ERROR);
        }
        //校验成功删除redis缓存
        stringRedisTemplate.delete(key);
    }
    /**
     * 缓存验证码，有效期15分钟
     * @param randomStr
     * @param blockX
     **/
    public void saveImageCode(String randomStr, String blockX) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(RedisKeyConstant.USER_LOGIN_CAPTCHA+randomStr, blockX, 15, TimeUnit.MINUTES);
    }

    /********************************生成验证码******************************************************/
    /**
     * 入参校验设置默认值
     **/
    public static void checkCaptcha(CaptchaDTO captcha) {
        //设置画布宽度默认值
        if (captcha.getCanvasWidth() == null) {
            captcha.setCanvasWidth(320);
        }
        //设置画布高度默认值
        if (captcha.getCanvasHeight() == null) {
            captcha.setCanvasHeight(155);
        }
        //设置阻塞块宽度默认值
        if (captcha.getBlockWidth() == null) {
            captcha.setBlockWidth(65);
        }
        //设置阻塞块高度默认值
        if (captcha.getBlockHeight() == null) {
            captcha.setBlockHeight(55);
        }
        //设置阻塞块凹凸半径默认值
        if (captcha.getBlockRadius() == null) {
            captcha.setBlockRadius(9);
        }
    }

    /**
     * 获取指定范围内的随机数
     **/
    public static int getNonceByRange(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start + 1) + start;
    }

    /**
     * 获取验证码资源图
     **/
    public static BufferedImage getBufferedImage() {
        try {
            //随机图片
            int nonce = RandomUtil.randomInt(10, 1000);
            //获取网络资源图片
            String imgUrl = String.format(NET_IMG_URL, nonce);
            URL url = new URL(imgUrl);
            return ImageIO.read(url.openStream());

        } catch (Exception e) {
            //获取本地图片
            int number = RandomUtil.randomInt(1, 4);
            String imgPath = String.format(CAPTCHA_IMG_PATH, number);

            try (InputStream is = CaptchaUtil.class.getResourceAsStream(imgPath)) {
                if (is == null) {
                    throw new IOException("Image resource not found: " + imgPath);
                }
                return ImageIO.read(is);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 调整图片大小
     **/
    public static BufferedImage imageResize(BufferedImage bufferedImage, int width, int height) {
        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resultImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();
        return resultImage;
    }

    /**
     * 抠图，并生成阻塞块
     **/
    public static void cutByTemplate(BufferedImage canvasImage, BufferedImage blockImage, int blockWidth, int blockHeight, int blockRadius, int blockX, int blockY) {
        BufferedImage waterImage = new BufferedImage(blockWidth, blockHeight, BufferedImage.TYPE_4BYTE_ABGR);
        //阻塞块的轮廓图
        int[][] blockData = getBlockData(blockWidth, blockHeight, blockRadius);
        //创建阻塞块具体形状
        for (int i = 0; i < blockWidth; i++) {
            for (int j = 0; j < blockHeight; j++) {
                try {
                    //原图中对应位置变色处理
                    if (blockData[i][j] == 1) {
                        //背景设置为黑色
                        waterImage.setRGB(i, j, Color.BLACK.getRGB());
                        blockImage.setRGB(i, j, canvasImage.getRGB(blockX + i, blockY + j));
                        //轮廓设置为白色，取带像素和无像素的界点，判断该点是不是临界轮廓点
                        if (blockData[i + 1][j] == 0 || blockData[i][j + 1] == 0 || blockData[i - 1][j] == 0 || blockData[i][j - 1] == 0) {
                            blockImage.setRGB(i, j, Color.WHITE.getRGB());
                            waterImage.setRGB(i, j, Color.WHITE.getRGB());
                        }
                    }
                    //这里把背景设为透明
                    else {
                        blockImage.setRGB(i, j, Color.TRANSLUCENT);
                        waterImage.setRGB(i, j, Color.TRANSLUCENT);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //防止数组下标越界异常
                }
            }
        }
        //在画布上添加阻塞块水印
        addBlockWatermark(canvasImage, waterImage, blockX, blockY);
    }

    /**
     * 构建拼图轮廓轨迹
     **/
    private static int[][] getBlockData(int blockWidth, int blockHeight, int blockRadius) {
        int[][] data = new int[blockWidth][blockHeight];
        double po = Math.pow(blockRadius, 2);
        //随机生成两个圆的坐标，在4个方向上 随机找到2个方向添加凸/凹
        //凸/凹1
        int face1 = RandomUtils.nextInt(0,4);
        //凸/凹2
        int face2;
        //保证两个凸/凹不在同一位置
        do {
            face2 = RandomUtils.nextInt(0,4);
        } while (face1 == face2);
        //获取凸/凹起位置坐标
        int[] circle1 = getCircleCoords(face1, blockWidth, blockHeight, blockRadius);
        int[] circle2 = getCircleCoords(face2, blockWidth, blockHeight, blockRadius);
        //随机凸/凹类型
        int shape = getNonceByRange(0, 1);
        //圆的标准方程 (x-a)²+(y-b)²=r²,标识圆心（a,b）,半径为r的圆
        //计算需要的小图轮廓，用二维数组来表示，二维数组有两张值，0和1，其中0表示没有颜色，1有颜色
        for (int i = 0; i < blockWidth; i++) {
            for (int j = 0; j < blockHeight; j++) {
                data[i][j] = 0;
                //创建中间的方形区域
                if ((i >= blockRadius && i <= blockWidth - blockRadius && j >= blockRadius && j <= blockHeight - blockRadius)) {
                    data[i][j] = 1;
                }
                double d1 = Math.pow(i - Objects.requireNonNull(circle1)[0], 2) + Math.pow(j - circle1[1], 2);
                double d2 = Math.pow(i - Objects.requireNonNull(circle2)[0], 2) + Math.pow(j - circle2[1], 2);
                //创建两个凸/凹
                if (d1 <= po || d2 <= po) {
                    data[i][j] = shape;
                }
            }
        }
        return data;
    }
    /**
     * 根据朝向获取圆心坐标
     */
    private static int[] getCircleCoords(int face, int blockWidth, int blockHeight, int blockRadius) {
        //上
        if (0 == face) {
            return new int[]{blockWidth / 2 - 1, blockRadius};
        }
        //左
        else if (1 == face) {
            return new int[]{blockRadius, blockHeight / 2 - 1};
        }
        //下
        else if (2 == face) {
            return new int[]{blockWidth / 2 - 1, blockHeight - blockRadius - 1};
        }
        //右
        else if (3 == face) {
            return new int[]{blockWidth - blockRadius - 1, blockHeight / 2 - 1};
        }
        return null;
    }
    /**
     * 在画布上添加阻塞块水印
     */
    private static void addBlockWatermark(BufferedImage canvasImage, BufferedImage blockImage, int x, int y) {
        Graphics2D graphics2D = canvasImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.8f));
        graphics2D.drawImage(blockImage, x, y, null);
        graphics2D.dispose();
    }
    /**
     * BufferedImage转BASE64
     */
    public static String toBase64(BufferedImage bufferedImage, String type) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, type, byteArrayOutputStream);
            String base64 = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            return String.format("data:image/%s;base64,%s", type, base64);
        } catch (IOException e) {
            log.error("图片资源转换BASE64失败");
            //异常处理
            return null;
        }
    }

}
