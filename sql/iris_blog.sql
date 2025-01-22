
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `author_id` int(11) NULL DEFAULT NULL COMMENT '文章作者的ID',
  `author_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者昵称',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '分类id',
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章标签,多个用逗号隔开,最多3个',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `cover_image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面地址',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文章简介',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
  `read_type` tinyint(1) NULL DEFAULT 0 COMMENT '阅读方式 0无需验证 1点赞阅读',
  `is_top` tinyint(1) NULL DEFAULT 0 COMMENT '是否置顶 0否 1是',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态 0：草稿 1：发布 2:下架',
  `is_forward` tinyint(1) NULL DEFAULT 1 COMMENT '是否转载  0：转载 1:原创',
  `forward_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '转载地址',
  `is_recommend` tinyint(1) NULL DEFAULT 0 COMMENT '是否推荐:0不推荐;1推荐',
  `likes` int(5) NULL DEFAULT NULL COMMENT '点赞数量',
  `collection` int(5) UNSIGNED NULL DEFAULT NULL COMMENT '收藏数量',
  `comment` int(5) NULL DEFAULT NULL COMMENT '评论数量',
  `views` int(5) NULL DEFAULT NULL COMMENT '阅读数量',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `image_details` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章详情图片,最多九个',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES (8, 1, 'admin', 100, '开源软件', '2,5,8', 'MarkDown语法', 'https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel1.jpeg', '开源软件', '## 一、Markdown语法\n\n### 1.1 标题\n\n```Markdown\n使用 `#` 可以表示标题，一级标题对应一个 `#` ，二级标题对应两个 `#` 号，最多至六级标题。在Typora中，`#` 后要紧接着一个空格才能表示标题，否则就是普通字符。\n\nTypora中，也可以使用快捷键Ctrl+1（2，3，4，5，6）表示相对应的标题。Ctrl+0表示段落。\n```\n\n```Markdown\n# 一级标题\n\n## 二级标题\n\n### 三级标题\n\n#### 四级标题\n\n##### 五级标题\n\n###### 六级标题\n```\n\n\n\n### 2.2 字体\n\n- 用一对星号`*`括住的文本表示斜体文本，如：`*`要变斜体的文本`*`，*斜体文本* ；\n- 也可以用一对下划线`_`括住文本来表示斜体文本，如：`_`要变斜体的文本`_`，*斜体文本* ；\n- Typora的快捷键`Ctrl+I`\n\n\n\n*这是斜体* \n\n_这是斜体_\n\n\n\n- 用一对`**`括住的文本表示粗体文本，如：`**`要变粗体的文本`**`，**粗体文本**；\n- 也可以用一对`__`括住的文本来表示粗体文本，如：`__`要变粗体的文本`__`，**粗体文本**；\n- Typora的快捷键`Ctrl+B`\n\n\n\n**这是粗体**\n\n__这是粗体__\n\n\n\n- 用一对`***`括住的文本表示粗斜体文本，如：`***`要变粗斜体的文本`***`，***粗斜体文本***；\n- 也可以用一对`___`括住的文本来表示粗斜体文本，如：`___`要变粗斜体的文本`___`，***粗斜体文本***；\n\n\n\n***这是斜粗体***\n\n___这是斜粗体___\n\n\n\n### 2.3 各种线\n\n- 分割线，可以使用**三个及以上**的 `+` 号或 `*` 号或 `-` 来表示一条分割线；\n\n由三个`*`号表示的分割线：\n\n---\n\n+++\n\n***\n\n------\n\n由三个`+`号表示的分割线：\n\n+++(在CSDN中不代表分割线）\n\n由三个`-`号表示的分割线：\n\n------\n\n- 删除线，可以使用一对`~~`括住的文本来表示删除文本，如：`~~`要加删除线的文本`~~`，删除文本；在Typora中，也可以使用快捷键**Alt+Shift+5**来加删除线，语法相同，删除线。\n\n~~这是要删除的文本~~\n\n\n\n- 下划线，可以使用HTML的标签`<u>`和`</u>`表示增加下划线的文本，如：`<u>`要增加下划线的文本`</u>`，下划线；在Typora中，也可以使用快捷键**Ctrl+U**来增加下划线，语法也是相同的，下划线。\n\n<u>这是下划线</u>\n\n### 2.4 列表\n\n**无序列表**\n\n可以使用`*`，`+`或`-`标记符号来表示无序列表项，记住要在标记符号后**添加一个空格**，语法显示如下：\n\n```markdown\n\n* 第一项\n* 第二项\n+ 第一项\n+ 第二项\n- 第一项\n- 第二项\n\n\n```\n\n\n\n* 第一项\n* 第二项\n\n+ 第一项\n+ 第二项\n\n- 第一项\n- 第二项\n\n**有序列表**\n\n可以使用**数字加上`.`再加上空格**来表示有序列表，语法如下：\n\n1. 第一项\n2. 第二项\n\n10. 第三项\n\n**嵌套列表**\n\n首先使用`*`、`+`或`-`进入列表，然后回车换行，会发现系统自动生成列表第二项，此时按下**Tab**键，列表第二项变为第一项的子列表。**按回车退出当前列表**。可以在无序列表中嵌套有序列表。\n\n- 一\n  - 1.1\n    - 1.1.1\n      - 1.1.1.2\n        - 1.1.1.3\n- 二\n  - 2.1\n    - 2.1.1\n    - 1. 有序列表第一项\n      2. 有序列表第二项\n\n\n**任务列表**\n语法: \n```xml\n- [x] 预习计算机网络\n- [ ] 复习java编程思想\n- [ ] 刷历年四六级考卷卷\n  - [ ] 2020 年期末试卷\n  - [ ] 2021 年期末试卷\n  - [ ] 2022 年期末试卷\n```\n\n- [x] 预习计算机网络\n- [ ] 复习java编程思想\n- [ ] 刷历年四六级考卷卷\n  - [ ] 2020 年期末试卷\n  - [ ] 2021 年期末试卷\n  - [ ] 2022 年期末试卷\n### 2.5 区块\n\n当我们想要引用别人的文章内容时，可以将其放在区块内。\n\n可以使用`>`加空格来表示区块。要退出区块，同样使用`Enter`键即可。\n\n\n\n>这是区块\n>区块也可以嵌套\n>\n>>二级区块\n>>\n>>>三级区块\n>>>\n>>>\n\n\n\n### 2.6 代码\n\n如果是一行代码，可以使用段内代码块来表示，用一对 **`**（数字1旁边的符号）括住代码。\n\n比如`System.out.println(\"hello world!\");`\n\n如果是代码段，那么可以使用**三个 ` 加Enter/空格+编程语言**来表示。如：  \n\n```java\npublic class HelloWorld {\n\n    public static void main(String[] args) {\n        System.out.println(\"hello world!\");\n    }\n}\n```\n\n### 2.7 链接\n\n链接的使用方式有两种语法，如下：\n\n```markdown\n[链接文字](链接地址)\n或\n<链接地址>\n```\n\n可以使用链接打开网页，示例如下：\n\n```markdown\n[百度](https://www.baidu.com/)\n<https://www.baidu.com/>\n```\n\n```markdown\n一个小圆点`.`表示当前目录，故`./LinkTest.md`表示当前目录下的LinkTest.md文件，`./img/LinkTest.png`表示当前目录下的img文件下的LinkTest.png文件。\n\n两个小圆点`..`表示上一级目录。\n```\n\n我们也可以使用链接来实现**页内跳转**，语法为：\n\n```\n链接文字](#标题文字)\n\n[跳转到第一章第一节](#1.1 标题)\n```\n\n### 2.8 图片\n\n```markdown\n![alt 属性文本](图片地址)\n\n![alt 属性文本](图片地址 \"可选标题\")\n\n```\n\n<img src=\"https://img-home.csdnimg.cn/images/20230724024159.png?origin_url=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%253A%252F%252Fhbimg.b0.upaiyun.com%252F9725480bbe636fa2423cd46dae87320093323a9a70dd-HKFjZU_fw658%26refer%3Dhttp%253A%252F%252Fhbimg.b0.upaiyun.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Dauto%3Fsec%3D1653643312%26t%3Dceebbcb5a762b18d97086e462d9e17b4&pos_id=img-flEs997v-1729058944480)\" alt=\"这是图片\" style=\"zoom:15%;\"/>\n\n### 2.9 表格\n\nMarkdown 制作表格使用 `|` 来分隔不同的单元格，使用 `-` 来分隔表头和其他行。\n\n语法如下：\n\n```markdown\n|表头|表头|表头|\n|---|---|----|\n|单元格|单元格|单元格|\n|单元格|单元格|单元格|\n\n```\n| 项目   |  价格 | 数量 |\n| ------ | ----: | :--: |\n| 计算机 | $1600 |  5   |\n| 手机   |   $12 |  12  |\n| 管线   |    $1 | 234  |\n\n- `:-`表示左对齐\n- `-:`表示右对齐\n- `:-:`表示中间对齐\n---\nTypora中，我们可以使用快捷键**Ctrl+T**来插入表格\n\n### 2.10 特殊符号\n\n```xml\n&copy; & &uml; &trade; &iexcl; &pound;\n&amp; &lt; &gt; &yen; &euro; &reg; &plusmn; &para; &sect; &brvbar; &macr; &laquo; &middot;\n\nX&sup2; Y&sup3; &frac34; &frac14; &times; &divide; &raquo;\n\n18&ordm;C &quot; &apos;\n```\n\n&copy; & &uml; &trade; &iexcl; &pound;\n&amp; &lt; &gt; &yen; &euro; &reg; &plusmn; &para; &sect; &brvbar; &macr; &laquo; &middot;\n\nX&sup2; Y&sup3; &frac34; &frac14; &times; &divide; &raquo;\n\n18&ordm;C &quot; &apos;\n\n---\n\n### 2.11 引用\n\n```xml\n> 引用：如果想要插入空白换行（即 `<br>` 标签），在插入处先键入两个以上的空格然后回车即可\n```\n\n> 引用：如果想要插入空白换行（即 `<br>` 标签），在插入处先键入两个以上的空格然后回车即可\n---\n\n### 2.12 Tip提示\n\n::: tip\n  在此输入内容\n:::\n::: warning\n  在此输入内容\n:::\n::: danger\n  在此输入内容\n:::\n::: details\n  内容\n:::\n## 三、Typora与数学公式\n\n\n\n### 3.1 如何插入数学公式\n\n在Typora中，有两种方法插入数学公式，语法为：\n\n```markdown\n\n第一种方法：$数学公式$ 第一种方法表示插入行内公式（内联公式），即可以将公式插入到一行中\n第二种方法：第二种方法表示插入行间公式（外联公式），即可以将公式插入到行与行之间\n$$\n数学公式\n$$\n如果要在行间公式内换行，在换行的位置使用\\\\\n```\n\n$1+1=2$\n$$\n1+1=2\n$$\n\n### 3.2 上标下标\n\n上下标可以使用`^`，~后跟相应的符号来实现。如：\n\n上标: x^2^  下标:H~2~O \n\n{a^12^,a~34~}\n\n### 3.3 根号\n\n我们可以使用`\\sqrt{}`来表示根号。如：\n\n```markdown\n$\\sqrt{2}$,$\\sqrt{5}$ \\sqrt[3]{4}，\\sqrt[4]{10}\n```\n\n$\\sqrt{5}$  $\\sqrt[3]{4}$\n\n### 3.4 上下水平线\n\n我们可以使用`\\underline{}`,`\\overline{}`来表示上下水平线，如：\n\n```markdown\n\n$下水平线：\\underline{a+b}$\n$上水平线：\\overline{a+b}$\n\n```\n\n$下水平线：\\underline{a+b}$\n$上水平线：\\overline{a+b}$\n\n### 3.5 上下水平大括号\n\n我们可以使用`\\overbrace{}` 和 `\\underbrace{}` 在表达式的上、下方给出一水平的大括号\n\n当然，我们也可以在括号上添加说明，使用语法为`\\overbrace{}^{}`和`\\underbrace{}_{}`\n\n```markdown\n\n$\\overbrace{x_1+x_2+x_3}$\n$\\underbrace{x_1+x_2+x_3}$\n\n$\\overbrace{x_1+x_2+x_3}^{3个元素}$\n$\\underbrace{x_1+x_2+x_3}_{3个元素}$\n\n```\n\n$\\overbrace{x_1+x_2+x_3}$\n$\\underbrace{x_1+x_2+x_3}$\n\n$\\overbrace{x_1+x_2+x_3}^{3个元素}$\n$\\underbrace{x_1+x_2+x_3}_{3个元素}$\n\n### 3.6 向量符号\n\n我们可以使用`\\vec{}`来表示单个字母向量，其实也可以表示多个字母，但不美观，另两个命令`\\overrightarrow{}` 和`\\overleftarrow{}`在定义从A 到B 的向量时非常有用。如：\n\n```markdown\n\n$\\vec{a}$\n$\\vec{AB}$\n$\\vec{ABC}$\n$\\overrightarrow{AB}$\n$\\overleftarrow{AB}$\n```\n\n$\\vec{a}$\n$\\vec{AB}$\n$\\vec{ABC}$\n$\\overrightarrow{AB}$\n$\\overleftarrow{AB}$\n\n### 3.7 分数\n\n我们可以使用`\\frac{}{}`来表示分数，如：\n\n```markdown\n\n$\\frac{1}{2}$\n$\\frac{\\sqrt{3}}{4}$\n```\n\n$\\frac{1}{2}$\n$\\frac{\\sqrt{3}}{4}$\n\n\n\n### 3.8 积分运算符\n\n积分运算符用`\\int` 来生成 ，用`\\int_{}^{}`来表示积分上下界，如：\n\n```markdown\n\n$\\int$\n$\\int_{1}^{2}$\n```\n\n$\\int$\n$$\n\\int_{1}^{2}\n$$\n\n\n### 3.9 求和运算符\n\n求和运算符可以使用`\\sum`来生成，用`\\sum_{}^{}`来表示求和上下界，如：\n\n```markdown\n\n$\\sum$\n$\\sum_{i=1}^{10}x_i$\n```\n\n$\\sum$\n$\\sum_{i=1}^{10}x_i$\n\n求和符号的上下标在内联公式里，表现为上面那样，当在外联公式里时，表现如下：\n$$\n\\sum_{i=1}^{10}x_i$\n$$\n\n### 3.10 连乘运算符\n\n连乘运算符用`\\prod{}`表示，同样地，上下标用`prod_{}^{}`表示，如：\n\n```markdown\n\n$\\prod$\n$\\prod_{i=1}^{10}x_i$\n$$\n\\prod_{i=1}^{10}x_i\n$$\n```\n\n$\\prod$\n$\\prod_{i=1}^{10}x_i$\n$$\n\\prod_{i=1}^{10}x_i\n$$\n\n### 3.11 特殊符号\n\n**希腊字母**\n\n```markdown\nα为\\alpha，β \\betaβ为\\beta，γ \\gammaγ为\\gamma，θ \\thetaθ为\\theta，ρ \\rhoρ为\\rho，λ \\lambdaλ为\\lambda，μ \\muμ为\\mu\n\nΔ \\DeltaΔ为\\Delta，π \\piπ为\\pi，Ω \\OmegaΩ为\\Omega\n```\n\n**关系运算符**\n\n大于>，大于等于为`\\geq`或`\\ge`\n\n小于<，小于等于为`\\leq`或`\\le`\n\n等于=，不等于为`\\neq`或`\\ne`\n\n$\\ge$ ,$\\le$ ,$\\ne$ \n\n**加减乘除**\n\n加+ 减-\n\n乘×为`\\times` 除为`\\div`\n\n$\\times$,$\\div$ \n\n\n\n### 3.12 矩阵表示\n\n- `\\begin{matrix}`和`\\end{matrix}`说明在它们之间的是矩阵\n- `1 & 2 & 3\\\\`表示第一行的元素，其中用`&`来分割每一个元素，用`\\\\`来换行\n\n```markdown\n\n$$\n\\begin{matrix}\n1 & 2 & 3\\\\\n4 & 5 & 6\\\\\n\\end{matrix}\n$$\n```\n\n$$\n\\begin{matrix}\n1 & 2 & 3\\\\\n4 & 5 & 6\\\\\n\\end{matrix}\n$$\n\n* 其实我们仅仅在`\\begin{matrix}`前面加了`\\left[`，在`\\end{matrix}`后面加了`\\right]`，这样就能正确显示括号了，那我们可以将`[]`改为`||`吗，当然可以，就简单地把`\\left[`，`\\right]`改为`\\left|`和`\\right|`即可。\n\n```markdown\n\n$$\n\\left[\\begin{matrix}\n1 & 2 & 3\\\\\n4 & 5 & 6\n\\end{matrix}\\right]\n$$\n```\n\n$$\n\\left[\\begin{matrix}\n1 & 2 & 3\\\\\n4 & 5 & 6\n\\end{matrix}\\right]\n$$\n\n### 3.13 方程组\n\n现在我们一一来解释：\n\n- `begin{equation}`与`\\end{euqation}`表示它们之间的为方程组。\n- `\\left\\{`和`\\right.`表示在方程组的左边加上`{`，在右边加上`.`，因为`{`在外联公式中有特殊的意义，因此需要在其前面加上转义字符`\\`。\n- `\\begin{array}`和`\\end{array}`表示它们之间的是数组，其实这也可以用来表示矩阵。\n- `{lr}`表示有两列，第一列的值靠左排列，用`l`表示，第二列的值靠右排列，用`r`表示，如果是中间对齐则为`c`。\n- 然后下面三行是方程式，用`&`分割，用`\\\\`换行。\n\n```bash\n$$\n\\begin{equation}\n\\left\\{\n             \\begin{array}{lr}\n             x=\\dfrac{3\\pi}{2}(1+2t)\\cos(\\dfrac{3\\pi}{2}(1+2t)), &  \\\\\n             y=s, & 0\\leq s\\leq L,|t|\\leq1.\\\\\n             z=\\dfrac{3\\pi}{2}(1+2t)\\sin(\\dfrac{3\\pi}{2}(1+2t)), &  \n             \\end{array}\n\\right.\n\\end{equation}\n$$\n```\n\n![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/62a8b6e839df38264f75bb72a04ac1b3.png)\n\n### 3.14 分段函数\n\n就是在`\\begin{equation}`前加`y=`即可。\n\n```bash\n\n$$\ny=\n\\begin{equation}\n	\\left\\{\n		\\begin{array}{lr}\n		x-1 & x \\leq 0\n		x+1 & x>0\n		\\end{array}\n	\\right. \n\\end{equation}\n$$\n\n```\n\n![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/6904ca9459a0fab0a11827701dda4d11.png)\n## 四、Typora与HTML\n\n### 4.1 改变字体颜色及大小\n\n我们可以使用`<font> </font>`标签来改变字体的颜色及大小，如：	\n\n```html\n\n<font size=3 color=\"red\">字体颜色为红色，大小为3</font>\n\n<font size=4 color=\"blue\">字体颜色为蓝色，大小为4</font>\n\n<font size=6 color=\"violet\">字体颜色为紫罗兰，大小为6</font>\n\n```\n\n<font size=3 color=\"red\">字体颜色为红色，大小为3</font>\n\n<font size=4 color=\"blue\">字体颜色为蓝色，大小为4</font>\n\n<font size=6 color=\"violet\">字体颜色为紫罗兰，大小为6</font>\n\n### 4.2 改变对齐方式\n\n我们可以改变字体的对齐方式，用标签`<p> </p>`加上属性`align`，如：\n\n```html\n\n<p align=\"left\">左对齐</p>\n<p align=\"center\">中间对齐</p>\n<p align=\"right\">右对齐</p>\n\n```\n\n<p align=\"left\">左对齐</p>\n<p align=\"center\">中间对齐</p>\n<p align=\"right\">右对齐</p>\n\n## 五、扩展用法\n\n### 5.1 插入emoji表情\n\n我们可以使用`:emoji:`的语法来插入表情，比如： :happy:的语法为`:happy:`\n\n🦀的语法为`:crab:` 🐴的语法为`:horse:` :question:\n\n### People\n\n```\n\n😄 :smile:	|😆 :laughing:	 \n😊 :blush:	😃 :smiley:	☺️ :relaxed:\n😏 :smirk:	😍 :heart_eyes:	😘 :kissing_heart:\n😚 :kissing_closed_eyes:	😳 :flushed:	😌 :relieved:\n😆 :satisfied:	😁 :grin:	😉 :wink:\n😜 :stuck_out_tongue_winking_eye:	😝 :stuck_out_tongue_closed_eyes:	😀 :grinning:\n😗 :kissing:	😙 :kissing_smiling_eyes:	😛 :stuck_out_tongue:\n😴 :sleeping:	😟 :worried:	😦 :frowning:\n😧 :anguished:	😮 :open_mouth:	😬 :grimacing:\n😕 :confused:	😯 :hushed:	😑 :expressionless:\n😒 :unamused:	😅 :sweat_smile:	😓 :sweat:\n😥 :disappointed_relieved:	😩 :weary:	😔 :pensive:\n😞 :disappointed:	😖 :confounded:	😨 :fearful:\n😰 :cold_sweat:	😣 :persevere:	😢 :cry:\n😭 :sob:	😂 :joy:	😲 :astonished:\n😱 :scream:	 	😫 :tired_face:\n😠 :angry:	😡 :rage:	😤 :triumph:\n😪 :sleepy:	😋 :yum:	😷 :mask:\n😎 :sunglasses:	😵 :dizzy_face:	👿 :imp:\n😈 :smiling_imp:	😐 :neutral_face:	😶 :no_mouth:\n😇 :innocent:	👽 :alien:	💛 :yellow_heart:\n💙 :blue_heart:	💜 :purple_heart:	❤️ :heart:\n💚 :green_heart:	💔 :broken_heart:	💓 :heartbeat:\n💗 :heartpulse:	💕 :two_hearts:	💞 :revolving_hearts:\n💘 :cupid:	💖 :sparkling_heart:	✨ :sparkles:\n⭐️ :star:	🌟 :star2:	💫 :dizzy:\n💥 :boom:	💥 :collision:	💢 :anger:\n❗️ :exclamation:	❓ :question:	❕ :grey_exclamation:\n❔ :grey_question:	💤 :zzz:	💨 :dash:\n💦 :sweat_drops:	🎶 :notes:	🎵 :musical_note:\n🔥 :fire:	💩 :hankey:	💩 :poop:\n💩 :shit:	👍 :+1:	👍 :thumbsup:\n👎 :-1:	👎 :thumbsdown:	👌 :ok_hand:\n👊 :punch:	👊 :facepunch:	✊ :fist:\n✌️ :v:	👋 :wave:	✋ :hand:\n✋ :raised_hand:	👐 :open_hands:	☝️ :point_up:\n👇 :point_down:	👈 :point_left:	👉 :point_right:\n🙌 :raised_hands:	🙏 :pray:	👆 :point_up_2:\n👏 :clap:	💪 :muscle:	🤘 :metal:\n🖕 :fu:	🚶 :walking:	🏃 :runner:\n🏃 :running:	👫 :couple:	👪 :family:\n👬 :two_men_holding_hands:	👭 :two_women_holding_hands:	💃 :dancer:\n👯 :dancers:	🙆 :ok_woman:	🙅 :no_good:\n💁 :information_desk_person:	🙋 :raising_hand:	👰 :bride_with_veil:\n🙎 :person_with_pouting_face:	🙍 :person_frowning:	🙇 :bow:\n:couplekiss: :couplekiss:	💑 :couple_with_heart:	💆 :massage:\n💇 :haircut:	💅 :nail_care:	👦 :boy:\n👧 :girl:	👩 :woman:	👨 :man:\n👶 :baby:	👵 :older_woman:	👴 :older_man:\n👱 :person_with_blond_hair:	👲 :man_with_gua_pi_mao:	👳 :man_with_turban:\n👷 :construction_worker:	👮 :cop:	👼 :angel:\n👸 :princess:	😺 :smiley_cat:	😸 :smile_cat:\n😻 :heart_eyes_cat:	😽 :kissing_cat:	😼 :smirk_cat:\n🙀 :scream_cat:	😿 :crying_cat_face:	😹 :joy_cat:\n😾 :pouting_cat:	👹 :japanese_ogre:	👺 :japanese_goblin:\n🙈 :see_no_evil:	🙉 :hear_no_evil:	🙊 :speak_no_evil:\n💂 :guardsman:	💀 :skull:	🐾 :feet:\n👄 :lips:	💋 :kiss:	💧 :droplet:\n👂 :ear:	👀 :eyes:	👃 :nose:\n👅 :tongue:	💌 :love_letter:	👤 :bust_in_silhouette:\n👥 :busts_in_silhouette:	💬 :speech_balloon:	💭 :thought_balloon:\nNature\n☀️ :sunny:	☔️ :umbrella:	☁️ :cloud:\n❄️ :snowflake:	⛄️ :snowman:	⚡️ :zap:\n🌀 :cyclone:	🌁 :foggy:	🌊 :ocean:\n🐱 :cat:	🐶 :dog:	🐭 :mouse:\n🐹 :hamster:	🐰 :rabbit:	🐺 :wolf:\n🐸 :frog:	🐯 :tiger:	🐨 :koala:\n🐻 :bear:	🐷 :pig:	🐽 :pig_nose:\n🐮 :cow:	🐗 :boar:	🐵 :monkey_face:\n🐒 :monkey:	🐴 :horse:	🐎 :racehorse:\n🐫 :camel:	🐑 :sheep:	🐘 :elephant:\n🐼 :panda_face:	🐍 :snake:	🐦 :bird:\n🐤 :baby_chick:	🐥 :hatched_chick:	🐣 :hatching_chick:\n🐔 :chicken:	🐧 :penguin:	🐢 :turtle:\n🐛 :bug:	🐝 :honeybee:	🐜 :ant:\n🐞 :beetle:	🐌 :snail:	🐙 :octopus:\n🐠 :tropical_fish:	🐟 :fish:	🐳 :whale:\n🐋 :whale2:	🐬 :dolphin:	🐄 :cow2:\n🐏 :ram:	🐀 :rat:	🐃 :water_buffalo:\n🐅 :tiger2:	🐇 :rabbit2:	🐉 :dragon:\n🐐 :goat:	🐓 :rooster:	🐕 :dog2:\n🐖 :pig2:	🐁 :mouse2:	🐂 :ox:\n🐲 :dragon_face:	🐡 :blowfish:	🐊 :crocodile:\n🐪 :dromedary_camel:	🐆 :leopard:	🐈 :cat2:\n🐩 :poodle:	🐾 :paw_prints:	💐 :bouquet:\n🌸 :cherry_blossom:	🌷 :tulip:	🍀 :four_leaf_clover:\n🌹 :rose:	🌻 :sunflower:	🌺 :hibiscus:\n🍁 :maple_leaf:	🍃 :leaves:	🍂 :fallen_leaf:\n🌿 :herb:	🍄 :mushroom:	🌵 :cactus:\n🌴 :palm_tree:	🌲 :evergreen_tree:	🌳 :deciduous_tree:\n🌰 :chestnut:	🌱 :seedling:	🌼 :blossom:\n🌾 :ear_of_rice:	🐚 :shell:	🌐 :globe_with_meridians:\n🌞 :sun_with_face:	🌝 :full_moon_with_face:	🌚 :new_moon_with_face:\n🌑 :new_moon:	🌒 :waxing_crescent_moon:	🌓 :first_quarter_moon:\n🌔 :waxing_gibbous_moon:	🌕 :full_moon:	🌖 :waning_gibbous_moon:\n🌗 :last_quarter_moon:	🌘 :waning_crescent_moon:	🌜 :last_quarter_moon_with_face:\n🌛 :first_quarter_moon_with_face:	🌔 :moon:	🌍 :earth_africa:\n🌎 :earth_americas:	🌏 :earth_asia:	🌋 :volcano:\n🌌 :milky_way:	⛅️ :partly_sunny:	 \nObject\n🎍 :bamboo:	💝 :gift_heart:	🎎 :dolls:\n🎒 :school_satchel:	🎓 :mortar_board:	🎏 :flags:\n🎆 :fireworks:	🎇 :sparkler:	🎐 :wind_chime:\n🎑 :rice_scene:	🎃 :jack_o_lantern:	👻 :ghost:\n🎅 :santa:	🎄 :christmas_tree:	🎁 :gift:\n🔔 :bell:	🔕 :no_bell:	🎋 :tanabata_tree:\n🎉 :tada:	🎊 :confetti_ball:	🎈 :balloon:\n🔮 :crystal_ball:	💿 :cd:	📀 :dvd:\n💾 :floppy_disk:	📷 :camera:	📹 :video_camera:\n🎥 :movie_camera:	💻 :computer:	📺 :tv:\n📱 :iphone:	☎️ :phone:	☎️ :telephone:\n📞 :telephone_receiver:	📟 :pager:	📠 :fax:\n💽 :minidisc:	📼 :vhs:	🔉 :sound:\n🔈 :speaker:	🔇 :mute:	📢 :loudspeaker:\n📣 :mega:	⌛️ :hourglass:	⏳ :hourglass_flowing_sand:\n⏰ :alarm_clock:	⌚️ :watch:	📻 :radio:\n📡 :satellite:	➿ :loop:	🔍 :mag:\n🔎 :mag_right:	🔓 :unlock:	🔒 :lock:\n🔏 :lock_with_ink_pen:	🔐 :closed_lock_with_key:	🔑 :key:\n💡 :bulb:	🔦 :flashlight:	🔆 :high_brightness:\n🔅 :low_brightness:	🔌 :electric_plug:	🔋 :battery:\n📲 :calling:	✉️ :email:	📫 :mailbox:\n📮 :postbox:	🛀 :bath:	🛁 :bathtub:\n🚿 :shower:	🚽 :toilet:	🔧 :wrench:\n🔩 :nut_and_bolt:	🔨 :hammer:	💺 :seat:\n💰 :moneybag:	💴 :yen:	💵 :dollar:\n💷 :pound:	💶 :euro:	💳 :credit_card:\n💸 :money_with_wings:	📧 :e-mail:	📥 :inbox_tray:\n📤 :outbox_tray:	✉️ :envelope:	📨 :incoming_envelope:\n📯 :postal_horn:	📪 :mailbox_closed:	📬 :mailbox_with_mail:\n📭 :mailbox_with_no_mail:	🚪 :door:	🚬 :smoking:\n💣 :bomb:	🔫 :gun:	🔪 :hocho:\n💊 :pill:	💉 :syringe:	📄 :page_facing_up:\n📃 :page_with_curl:	📑 :bookmark_tabs:	📊 :bar_chart:\n📈 :chart_with_upwards_trend:	📉 :chart_with_downwards_trend:	📜 :scroll:\n📋 :clipboard:	📆 :calendar:	📅 :date:\n📇 :card_index:	📁 :file_folder:	📂 :open_file_folder:\n✂️ :scissors:	📌 :pushpin:	📎 :paperclip:\n✒️ :black_nib:	✏️ :pencil2:	📏 :straight_ruler:\n📐 :triangular_ruler:	📕 :closed_book:	📗 :green_book:\n📘 :blue_book:	📙 :orange_book:	📓 :notebook:\n📔 :notebook_with_decorative_cover:	📒 :ledger:	📚 :books:\n🔖 :bookmark:	📛 :name_badge:	🔬 :microscope:\n🔭 :telescope:	📰 :newspaper:	🏈 :football:\n🏀 :basketball:	⚽️ :soccer:	⚾️ :baseball:\n🎾 :tennis:	🎱 :8ball:	🏉 :rugby_football:\n🎳 :bowling:	⛳️ :golf:	🚵 :mountain_bicyclist:\n🚴 :bicyclist:	🏇 :horse_racing:	🏂 :snowboarder:\n🏊 :swimmer:	🏄 :surfer:	🎿 :ski:\n♠️ :spades:	♥️ :hearts:	♣️ :clubs:\n♦️ :diamonds:	💎 :gem:	💍 :ring:\n🏆 :trophy:	🎼 :musical_score:	🎹 :musical_keyboard:\n🎻 :violin:	👾 :space_invader:	🎮 :video_game:\n🃏 :black_joker:	🎴 :flower_playing_cards:	🎲 :game_die:\n🎯 :dart:	🀄️ :mahjong:	🎬 :clapper:\n📝 :memo:	📝 :pencil:	📖 :book:\n🎨 :art:	🎤 :microphone:	🎧 :headphones:\n🎺 :trumpet:	🎷 :saxophone:	🎸 :guitar:\n👞 :shoe:	👡 :sandal:	👠 :high_heel:\n💄 :lipstick:	👢 :boot:	👕 :shirt:\n👕 :tshirt:	👔 :necktie:	👚 :womans_clothes:\n👗 :dress:	🎽 :running_shirt_with_sash:	👖 :jeans:\n👘 :kimono:	👙 :bikini:	🎀 :ribbon:\n🎩 :tophat:	👑 :crown:	👒 :womans_hat:\n👞 :mans_shoe:	🌂 :closed_umbrella:	💼 :briefcase:\n👜 :handbag:	👝 :pouch:	👛 :purse:\n👓 :eyeglasses:	🎣 :fishing_pole_and_fish:	☕️ :coffee:\n🍵 :tea:	🍶 :sake:	🍼 :baby_bottle:\n🍺 :beer:	🍻 :beers:	🍸 :cocktail:\n🍹 :tropical_drink:	🍷 :wine_glass:	🍴 :fork_and_knife:\n🍕 :pizza:	🍔 :hamburger:	🍟 :fries:\n🍗 :poultry_leg:	🍖 :meat_on_bone:	🍝 :spaghetti:\n🍛 :curry:	🍤 :fried_shrimp:	🍱 :bento:\n🍣 :sushi:	🍥 :fish_cake:	🍙 :rice_ball:\n🍘 :rice_cracker:	🍚 :rice:	🍜 :ramen:\n🍲 :stew:	🍢 :oden:	🍡 :dango:\n🥚 :egg:	🍞 :bread:	🍩 :doughnut:\n🍮 :custard:	🍦 :icecream:	🍨 :ice_cream:\n🍧 :shaved_ice:	🎂 :birthday:	🍰 :cake:\n🍪 :cookie:	🍫 :chocolate_bar:	🍬 :candy:\n🍭 :lollipop:	🍯 :honey_pot:	🍎 :apple:\n🍏 :green_apple:	🍊 :tangerine:	🍋 :lemon:\n🍒 :cherries:	🍇 :grapes:	🍉 :watermelon:\n🍓 :strawberry:	🍑 :peach:	🍈 :melon:\n🍌 :banana:	🍐 :pear:	🍍 :pineapple:\n🍠 :sweet_potato:	🍆 :eggplant:	🍅 :tomato:\n🌽 :corn:	 	 \n\n\n```\n\n### Places\n\n```\n\n🏠 :house:	🏡 :house_with_garden:	🏫 :school:\n🏢 :office:	🏣 :post_office:	🏥 :hospital:\n🏦 :bank:	🏪 :convenience_store:	🏩 :love_hotel:\n🏨 :hotel:	💒 :wedding:	⛪️ :church:\n🏬 :department_store:	🏤 :european_post_office:	🌇 :city_sunrise:\n🌆 :city_sunset:	🏯 :japanese_castle:	🏰 :european_castle:\n⛺️ :tent:	🏭 :factory:	🗼 :tokyo_tower:\n🗾 :japan:	🗻 :mount_fuji:	🌄 :sunrise_over_mountains:\n🌅 :sunrise:	🌠 :stars:	🗽 :statue_of_liberty:\n🌉 :bridge_at_night:	🎠 :carousel_horse:	🌈 :rainbow:\n🎡 :ferris_wheel:	⛲️ :fountain:	🎢 :roller_coaster:\n🚢 :ship:	🚤 :speedboat:	⛵️ :boat:\n⛵️ :sailboat:	🚣 :rowboat:	⚓️ :anchor:\n🚀 :rocket:	✈️ :airplane:	🚁 :helicopter:\n🚂 :steam_locomotive:	🚊 :tram:	🚞 :mountain_railway:\n🚲 :bike:	🚡 :aerial_tramway:	🚟 :suspension_railway:\n🚠 :mountain_cableway:	🚜 :tractor:	🚙 :blue_car:\n🚘 :oncoming_automobile:	🚗 :car:	🚗 :red_car:\n🚕 :taxi:	🚖 :oncoming_taxi:	🚛 :articulated_lorry:\n🚌 :bus:	🚍 :oncoming_bus:	🚨 :rotating_light:\n🚓 :police_car:	🚔 :oncoming_police_car:	🚒 :fire_engine:\n🚑 :ambulance:	🚐 :minibus:	🚚 :truck:\n🚋 :train:	🚉 :station:	🚆 :train2:\n🚅 :bullettrain_front:	🚄 :bullettrain_side:	🚈 :light_rail:\n🚝 :monorail:	🚃 :railway_car:	🚎 :trolleybus:\n🎫 :ticket:	⛽️ :fuelpump:	🚦 :vertical_traffic_light:\n🚥 :traffic_light:	⚠️ :warning:	🚧 :construction:\n🔰 :beginner:	🏧 :atm:	🎰 :slot_machine:\n🚏 :busstop:	💈 :barber:	♨️ :hotsprings:\n🏁 :checkered_flag:	🎌 :crossed_flags:	🏮 :izakaya_lantern:\n🗿 :moyai:	🎪 :circus_tent:	🎭 :performing_arts:\n📍 :round_pushpin:	🚩 :triangular_flag_on_post:	🇯🇵 :jp:\n🇰🇷 :kr:	🇨🇳 :cn:	🇺🇸 :us:\n🇫🇷 :fr:	🇪🇸 :es:	🇮🇹 :it:\n🇷🇺 :ru:	🇬🇧 :gb:	🇬🇧 :uk:\n🇩🇪 :de:	 	 \n\n```\n\n**Symbols**\n\n```\n\n1️⃣ :one:	2️⃣ :two:	3️⃣ :three:\n4️⃣ :four:	5️⃣ :five:	6️⃣ :six:\n7️⃣ :seven:	8️⃣ :eight:	9️⃣ :nine:\n🔟 :keycap_ten:	🔢 :1234:	0️⃣ :zero:\n#️⃣ :hash:	🔣 :symbols:	◀️ :arrow_backward:\n⬇️ :arrow_down:	▶️ :arrow_forward:	⬅️ :arrow_left:\n🔠 :capital_abcd:	🔡 :abcd:	🔤 :abc:\n↙️ :arrow_lower_left:	↘️ :arrow_lower_right:	➡️ :arrow_right:\n⬆️ :arrow_up:	↖️ :arrow_upper_left:	↗️ :arrow_upper_right:\n⏬ :arrow_double_down:	⏫ :arrow_double_up:	🔽 :arrow_down_small:\n⤵️ :arrow_heading_down:	⤴️ :arrow_heading_up:	↩️:leftwards_arrow_with_hook:\n↪️ :arrow_right_hook:	↔️ :left_right_arrow:	↕️ :arrow_up_down:\n🔼 :arrow_up_small:	🔃 :arrows_clockwise:	🔄 :arrows_counterclockwise:\n⏪ :rewind:	⏩ :fast_forward:	ℹ️ :information_source:\n🆗 :ok:	🔀 :twisted_rightwards_arrows:	🔁 :repeat:\n🔂 :repeat_one:	🆕 :new:	🔝 :top:\n🆙 :up:	🆒 :cool:	🆓 :free:\n🆖 :ng:	🎦 :cinema:	🈁 :koko:\n📶 :signal_strength:	🈹 :u5272:	🈴 :u5408:\n🈺 :u55b6:	🈯️ :u6307:	🈷️ :u6708:\n🈶 :u6709:	🈵 :u6e80:	🈚️ :u7121:\n🈸 :u7533:	🈳 :u7a7a:	🈲 :u7981:\n🈂️ :sa:	🚻 :restroom:	🚹 :mens:\n🚺 :womens:	🚼 :baby_symbol:	🚭 :no_smoking:\n🅿️ :parking:	♿️ :wheelchair:	🚇 :metro:\n🛄 :baggage_claim:	🉑 :accept:	🚾 :wc:\n🚰 :potable_water:	🚮 :put_litter_in_its_place:	㊙️ :secret:\n㊗️ :congratulations:	Ⓜ️ :m:	🛂 :passport_control:\n🛅 :left_luggage:	🛃 :customs:	🉐 :ideograph_advantage:\n🆑 :cl:	🆘 :sos:	🆔 :id:\n🚫 :no_entry_sign:	🔞 :underage:	📵 :no_mobile_phones:\n🚯 :do_not_litter:	🚱 :non-potable_water:	🚳 :no_bicycles:\n🚷 :no_pedestrians:	🚸 :children_crossing:	⛔️ :no_entry:\n✳️ :eight_spoked_asterisk:	✴️ :eight_pointed_black_star:	💟 :heart_decoration:\n🆚 :vs:	📳 :vibration_mode:	📴 :mobile_phone_off:\n💹 :chart:	💱 :currency_exchange:	♈️ :aries:\n♉️ :taurus:	♊️ :gemini:	♋️ :cancer:\n♌️ :leo:	♍️ :virgo:	♎️ :libra:\n♏️ :scorpius:	♐️ :sagittarius:	♑️ :capricorn:\n♒️ :aquarius:	♓️ :pisces:	⛎ :ophiuchus:\n🔯 :six_pointed_star:	❎:negative_squared_cross_mark:	🅰️ :a:\n🅱️ :b:	🆎 :ab:	🅾️ :o2:\n💠:diamond_shape_with_a_dot_inside:	♻️ :recycle:	🔚 :end:\n🔛 :on:	🔜 :soon:	🕐 :clock1:\n🕜 :clock130:	🕙 :clock10:	🕥 :clock1030:\n🕚 :clock11:	🕦 :clock1130:	🕛 :clock12:\n🕧 :clock1230:	🕑 :clock2:	🕝 :clock230:\n🕒 :clock3:	🕞 :clock330:	🕓 :clock4:\n🕟 :clock430:	🕔 :clock5:	🕠 :clock530:\n🕕 :clock6:	🕡 :clock630:	🕖 :clock7:\n🕢 :clock730:	🕗 :clock8:	🕣 :clock830:\n🕘 :clock9:	🕤 :clock930:	💲 :heavy_dollar_sign:\n©️ :copyright:	®️ :registered:	™️ :tm:\n❌ :x:	❗️ :heavy_exclamation_mark:	‼️ :bangbang:\n⁉️ :interrobang:	⭕️ :o:	✖️ :heavy_multiplication_x:\n➕ :heavy_plus_sign:	➖ :heavy_minus_sign:	➗ :heavy_division_sign:\n💮 :white_flower:	💯 :100:	✔️ :heavy_check_mark:\n☑️ :ballot_box_with_check:	🔘 :radio_button:	🔗 :link:\n➰ :curly_loop:	〰️ :wavy_dash:	〽️ :part_alternation_mark:\n🔱 :trident:	:black_square: :black_square:	:white_square: :white_square:\n✅ :white_check_mark:	🔲 :black_square_button:	🔳 :white_square_button:\n⚫️ :black_circle:	⚪️ :white_circle:	🔴 :red_circle:\n🔵 :large_blue_circle:	🔷 :large_blue_diamond:	🔶 :large_orange_diamond:\n🔹 :small_blue_diamond:	🔸 :small_orange_diamond:	🔺 :small_red_triangle:\n🔻 :small_red_triangle_down:	 	 \n\n\n```\n\n\n\n\n\n### 5.2 插入目录\n\n当我们为使用标题将文分章节后，可以在输入`[toc]`命令的地方自动根据标题生成目录。\n\n### 5.3文本高亮\n\n在Typora中，可以用一对`==`将要高亮的文本括起来，如：\n\n==要高亮的文本==\n==背景会用黄色填充==\n\n\n\n## 六、流程图、时序图(顺序图)、甘特图\n\nTypora内置了对Mermaid的支持，才阔以画各种图。在 Typora 中，输入 **```mermaid** 然后敲击回车，即可初始化一张空白图。\n\n### 6.1 横向流程图\n\n```txt\n\n~~~mermaid\ngraph LR\n \nA[方形] -->B(圆角)\n    B --> C{条件a}\n    C -->|a=1| D[结果1]\n    C -->|a=2| E[结果2]\n    F[横向流程图]\n~~~\n```\n\n![](https://admin.lstar.icu:9090/file/image/20241016/flow_heng.png)\n\n\n### 6.2  竖向流程图\n\n```\n\n~~~mermaid\ngraph TD\nA[方形] -->B(圆角)\n    B --> C{条件a}\n    C -->|a=1| D[结果1]\n    C -->|a=2| E[结果2]\n    F[竖向流程图]\n~~~\n```\n\n![](https://admin.lstar.icu:9090/file/image/20241016/flow_shu.png)\n\n\n\n\n### 6.3 标准流程图\n\n```\n\n~~~flow\nst=>start: 开始框\n \nop=>operation: 处理框\n \ncond=>condition: 判断框(是或否?)\n \nsub1=>subroutine: 子流程\n \nio=>inputoutput: 输入输出框\n \ne=>end: 结束框\n \nst->op->cond\n \ncond(yes)->io->e\n \ncond(no)->sub1(right)->op\n~~~\n```\n\n![](https://admin.lstar.icu:9090/file/image/20241016/biaozhun_flow_shu.png)\n\n\n\n### 6.4 标准流程图（横向）\n\n```\n\n~~~flow\nst=>start: 开始框\n \nop=>operation: 处理框\n \ncond=>condition: 判断框(是或否?)\n \nsub1=>subroutine: 子流程\n \nio=>inputoutput: 输入输出框\n \ne=>end: 结束框\n \nst(right)->op(right)->cond\n \ncond(yes)->io(bottom)->e\n \ncond(no)->sub1(right)->op\n~~~\n```\n\n![](https://admin.lstar.icu:9090/file/image/20241016/biaozhun_flow_heng.png)\n\n\n\n### 6.5 UML[时序图]\n\n```\n\n~~~sequence\nlyp_csdn_对象A->lyp_csdn_对象B: 对象B你好吗?（请求）\n \nNote right of lyp_csdn_对象B: 对象B的描述\n \nNote left of lyp_csdn_对象A: 对象A的描述(提示)\n \nlyp_csdn_对象B-->lyp_csdn_对象A: 我很好(响应)\n \nlyp_csdn_对象A->lyp_csdn_对象B: 你真的好吗？\n~~~\n```\n\n\n![](https://admin.lstar.icu:9090/file/image/20241016/uml_shixun.png)\n\n\n\n### 6.6 UML时序图（复杂样例）\n\n```\n\n~~~sequence\nTitle: ULM时序图复杂使用（标题）\n \n对象A->对象B: 对象B你好吗?（请求）\n \nNote right of 对象B: 对象B的描述\n \nNote left of 对象A: 对象A的描述(提示)\n \n对象B-->对象A: 我很好(响应)\n \n对象B->小三: 你好吗\n \n小三-->>对象A: 对象B找我了\n \n对象A->对象B: 你真的好吗？\n \nNote over 小三,对象B: 我们是朋友\n \nparticipant C\n \nNote right of C: 没人陪我玩\n~~~\n```\n\n![](https://admin.lstar.icu:9090/file/image/20241016/uml_shixun_fuza.png)\n\n### 6.7 UML标准时序图\n\n```\n\n~~~mermaid\n%% 时序图例子,-> 直线，-->虚线，->>实线箭头\n \n  sequenceDiagram\n \n    participant 张三\n \n    participant 李四\n \n    张三->王五: 王五你好吗？\n \n    loop 健康检查\n \n        王五->王五: 与疾病战斗\n \n    end\n \n    Note right of 王五: 合理 食物 <br/>看医生...\n \n    李四-->>张三: 很好!\n \n    王五->李四: 你怎么样?\n \n    李四-->王五: 很好!\n~~~\n```\n\n\n![](https://admin.lstar.icu:9090/file/image/20241016/uml_biaozhun_shixun.png)\n\n\n### 6.8 [甘特图]\n\n```\n\n%% 语法示例\n        gantt\n        dateFormat  YYYY-MM-DD\n        title 软件开发甘特图\n        section 设计\n        需求                      :done,    des1, 2014-01-06,2014-01-08\n        原型                      :active,  des2, 2014-01-09, 3d\n        UI设计                     :         des3, after des2, 5d\n    未来任务                     :         des4, after des3, 5d\n        section 开发\n        学习准备理解需求                      :crit, done, 2014-01-06,24h\n        设计框架                             :crit, done, after des2, 2d\n        开发                                 :crit, active, 3d\n        未来任务                              :crit, 5d\n        耍                                   :2d\n \n        section 测试\n        功能测试                              :active, a1, after des3, 3d\n        压力测试                               :after a1  , 20h\n        测试报告                               : 48h\n```\n\n![](https://admin.lstar.icu:9090/file/image/20241016/gantetu.png)\n\n\n\n### 6.9 类图\n\n语法解释：`<|--` 表示继承，`+` 表示 `public`，`-` 表示 `private`，学过 Java 的应该都知道。\n\n```\n\nclassDiagram\n      Animal <|-- Duck\n      Animal <|-- Fish\n      Animal <|-- Zebra\n      Animal : +int age\n      Animal : +String gender\n      Animal: +isMammal()\n      Animal: +mate()\n      class Duck{\n          +String beakColor\n          +swim()\n          +quack()\n      }\n      class Fish{\n          -int sizeInFeet\n          -canEat()\n      }\n      class Zebra{\n          +bool is_wild\n          +run()\n      }\n```\n\n![](https://admin.lstar.icu:9090/file/image/20241016/leitu.png)\n\n\n\n\n### 6.10 **状态图**\n\n```\n\nstateDiagram\n    [*] --> s1\n    s1 --> [*]\n```\n\n![](https://admin.lstar.icu:9090/file/image/20241016/status_tu.png)\n\n\n\n\n### 6.11 **饼图**\n\n```\n\npie\n    title Key elements in Product X\n    \"Calcium\" : 42.96\n    \"Potassium\" : 50.05\n    \"Magnesium\" : 10.01\n    \"Iron\" :  5\n```\n\n![](https://admin.lstar.icu:9090/file/image/20241016/bing_tu.png)\n\n\n', 1, 0, 1, 1, '', 1, NULL, NULL, NULL, 123123, '2024-06-11 15:58:14', '2024-10-29 17:34:35', NULL);

-- ----------------------------
-- Table structure for t_article_category
-- ----------------------------
DROP TABLE IF EXISTS `t_article_category`;
CREATE TABLE `t_article_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_name`(`name`) USING BTREE COMMENT '分类名称'
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article_category
-- ----------------------------
INSERT INTO `t_article_category` VALUES (1, '后端开发', 1, 'ep:cloudy', '2024-06-03 11:41:52', '2024-06-03 11:52:46');
INSERT INTO `t_article_category` VALUES (2, '前端开发', 2, 'ep:data-line', '2024-06-03 11:42:31', '2024-06-03 11:49:02');
INSERT INTO `t_article_category` VALUES (3, '运维部署', 3, 'fa:server', '2024-06-03 11:44:12', '2024-06-03 11:51:28');
INSERT INTO `t_article_category` VALUES (4, '软件资源', 5, 'ep:coordinate', '2024-06-03 11:45:24', '2024-06-03 11:45:24');
INSERT INTO `t_article_category` VALUES (5, '数据库', 4, 'ep:coin', '2024-06-03 11:45:36', '2024-06-03 11:45:36');
INSERT INTO `t_article_category` VALUES (6, '心情随笔', 6, 'fa-solid:book-open', '2024-06-03 11:46:31', '2024-06-03 11:46:31');
INSERT INTO `t_article_category` VALUES (7, '踩坑记', 7, 'fa:bug', '2024-06-03 11:47:49', '2024-06-03 11:47:49');
INSERT INTO `t_article_category` VALUES (13, '感悟', 0, 'ep:comment', '2024-06-08 16:05:44', '2024-08-02 16:36:23');
INSERT INTO `t_article_category` VALUES (100, '开源软件', 100, 'ep:promotion', '2024-10-29 17:35:51', '2024-10-29 17:36:07');

-- ----------------------------
-- Table structure for t_article_collect
-- ----------------------------
DROP TABLE IF EXISTS `t_article_collect`;
CREATE TABLE `t_article_collect`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article_collect
-- ----------------------------

-- ----------------------------
-- Table structure for t_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag`;
CREATE TABLE `t_article_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_name`(`name`) USING BTREE COMMENT '标签名称'
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article_tag
-- ----------------------------
INSERT INTO `t_article_tag` VALUES (1, 'Spring Boot', 1, '2024-06-03 13:25:59', '2024-06-03 13:25:59');
INSERT INTO `t_article_tag` VALUES (2, 'Mysql', 2, '2024-06-03 13:26:11', '2024-06-03 13:26:11');
INSERT INTO `t_article_tag` VALUES (3, 'Kubernetes', 3, '2024-06-03 13:26:56', '2024-06-03 13:26:56');
INSERT INTO `t_article_tag` VALUES (4, 'Redis', 4, '2024-06-03 13:27:41', '2024-06-03 13:27:41');
INSERT INTO `t_article_tag` VALUES (5, 'Linux', 5, '2024-06-03 13:27:50', '2024-06-03 13:27:50');
INSERT INTO `t_article_tag` VALUES (6, 'Nginx', 6, '2024-06-03 13:28:05', '2024-06-03 13:28:05');
INSERT INTO `t_article_tag` VALUES (7, 'Vue', 7, '2024-06-03 13:28:29', '2024-06-03 13:28:29');
INSERT INTO `t_article_tag` VALUES (8, 'Spring Cloud', 8, '2024-06-03 13:28:59', '2024-06-03 13:28:59');
INSERT INTO `t_article_tag` VALUES (9, 'Java', 9, '2024-06-03 13:40:06', '2024-06-03 13:40:06');
INSERT INTO `t_article_tag` VALUES (10, '心情', 0, '2024-06-08 16:05:44', '2024-06-08 16:05:44');

-- ----------------------------
-- Table structure for t_carousel
-- ----------------------------
DROP TABLE IF EXISTS `t_carousel`;
CREATE TABLE `t_carousel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '轮播图地址',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否显示 (0否 1是)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '首页轮播' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_carousel
-- ----------------------------
INSERT INTO `t_carousel` VALUES (5, 'https://img2.baidu.com/it/u=3192794213,2397967807&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800', 1, '2024-08-05 14:11:28', '2024-10-29 17:38:27');
INSERT INTO `t_carousel` VALUES (6, 'https://img1.baidu.com/it/u=3407875929,841654473&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800', 1, '2024-08-05 14:13:05', '2024-10-29 17:38:37');
INSERT INTO `t_carousel` VALUES (7, 'https://img2.baidu.com/it/u=2777280900,1082757912&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=500', 1, '2024-08-05 14:14:36', '2024-10-29 17:38:46');
INSERT INTO `t_carousel` VALUES (8, 'https://img1.baidu.com/it/u=2222368312,1157616283&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500', 1, '2024-08-05 13:52:41', '2024-10-29 17:38:58');
INSERT INTO `t_carousel` VALUES (9, 'https://img0.baidu.com/it/u=557240053,3924624262&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500', 1, '2024-10-29 17:39:20', '2024-10-29 17:39:20');
INSERT INTO `t_carousel` VALUES (10, 'https://img2.baidu.com/it/u=923887576,586720797&fm=253&fmt=auto&app=120&f=JPEG?w=889&h=500', 1, '2024-10-29 17:39:49', '2024-10-29 17:39:49');
INSERT INTO `t_carousel` VALUES (11, 'https://img0.baidu.com/it/u=2914840137,3999720973&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500', 1, '2024-10-29 17:39:55', '2024-10-29 17:39:55');

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '评论人ID',
  `comment_type` tinyint(1) NOT NULL COMMENT '评论类型:1文章,2说说,3友链',
  `type_id` bigint(20) NULL DEFAULT NULL COMMENT '类型id',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `reply_user_id` bigint(20) NULL DEFAULT NULL COMMENT '回复人id',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父id',
  `reply_id` bigint(20) NULL DEFAULT NULL COMMENT '回复评论id',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态:0未审核,1审核通过,2驳回',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES (1, 1, 1, 1, '这是测试', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:02');
INSERT INTO `t_comment` VALUES (3, 1, 2, 1, '这是测试说说', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-09-03 10:38:59');
INSERT INTO `t_comment` VALUES (4, 1, 3, 1, '这是测试友链', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:02');
INSERT INTO `t_comment` VALUES (76, 1, 3, 1, '这是测试1', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');
INSERT INTO `t_comment` VALUES (77, 1, 3, 1, '这是测试12', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');
INSERT INTO `t_comment` VALUES (78, 1, 3, 1, '这是测试122', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');
INSERT INTO `t_comment` VALUES (79, 1, 3, 1, '这是测试13', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');
INSERT INTO `t_comment` VALUES (80, 1, 3, 1, '这是测试133', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');
INSERT INTO `t_comment` VALUES (81, 1, 3, 1, '这是测试1333', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');
INSERT INTO `t_comment` VALUES (82, 1, 3, 1, '这是测试1345', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');
INSERT INTO `t_comment` VALUES (83, 1, 3, 1, '这是测试14', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');
INSERT INTO `t_comment` VALUES (84, 1, 3, 1, '这是测试141', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');
INSERT INTO `t_comment` VALUES (85, 1, 3, 1, '这是测试1413', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');
INSERT INTO `t_comment` VALUES (86, 1, 3, 1, '这是测试14134', 2, 1, NULL, '127.0.0.1', '全球', 'Chrome 123-Windows 10', 1, '2024-05-31 10:32:11', '2024-10-29 18:01:03');

-- ----------------------------
-- Table structure for t_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_data`;
CREATE TABLE `t_dict_data`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型',
  `dict_label` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典标签',
  `dict_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_dict_type_value`(`dict_type`, `dict_value`) USING BTREE,
  INDEX `idx_sort`(`sort`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dict_data
-- ----------------------------
INSERT INTO `t_dict_data` VALUES (1, 'system_boolean_status', '开启', 'true', '开启', 1, '2024-05-28 17:12:00', '2024-05-28 18:12:19');
INSERT INTO `t_dict_data` VALUES (2, 'system_boolean_status', '关闭', 'false', '关闭', 0, '2024-05-28 17:12:35', '2024-05-30 09:43:29');
INSERT INTO `t_dict_data` VALUES (3, 'system_number_status', '开启', '1', '状态开启', 1, '2024-05-28 17:20:20', '2024-05-28 18:12:13');
INSERT INTO `t_dict_data` VALUES (4, 'system_number_status', '关闭', '0', '状态关闭', 2, '2024-05-28 17:20:31', '2024-05-28 18:12:13');
INSERT INTO `t_dict_data` VALUES (5, 'system_user_sex', '女', '0', '用户性别:女', 1, '2024-05-28 17:20:58', '2024-05-28 18:11:54');
INSERT INTO `t_dict_data` VALUES (6, 'system_user_sex', '男', '1', '用户性别:男', 2, '2024-05-28 17:21:13', '2024-05-28 18:11:54');
INSERT INTO `t_dict_data` VALUES (7, 'system_user_sex', '未知', '2', '用户性别:未知', 3, '2024-05-28 17:21:41', '2024-05-28 18:11:54');
INSERT INTO `t_dict_data` VALUES (8, 'system_request_type', 'GET', 'GET', '请求方式:GET请求', 1, '2024-05-28 18:21:22', '2024-05-30 09:42:50');
INSERT INTO `t_dict_data` VALUES (9, 'system_request_type', 'POST', 'POST', 'POST请求', 2, '2024-05-28 18:22:44', '2024-05-30 09:42:47');
INSERT INTO `t_dict_data` VALUES (10, 'system_request_type', 'PUT', 'PUT', 'PUT请求', 3, '2024-05-28 18:22:57', '2024-05-30 09:42:43');
INSERT INTO `t_dict_data` VALUES (11, 'system_request_type', 'DELETE', 'DELETE', 'DELETE请求', 4, '2024-05-28 18:23:10', '2024-05-30 09:42:40');
INSERT INTO `t_dict_data` VALUES (12, 'system_request_type', 'HEAD', 'HEAD', 'HEAD请求', 5, '2024-05-28 18:23:30', '2024-05-30 09:42:35');
INSERT INTO `t_dict_data` VALUES (13, 'system_config_type', '非系统配置', 'true', '系统配置类型:非系统配置', 1, '2024-05-30 09:51:46', '2024-05-30 09:51:46');
INSERT INTO `t_dict_data` VALUES (14, 'system_config_type', '系统配置', 'false', '系统配置类型:系统配置', 2, '2024-05-30 09:52:10', '2024-05-30 09:52:10');
INSERT INTO `t_dict_data` VALUES (15, 'system_task_error_policy', '立即执行', '1', '定时任务错误策略:立即执行', 1, '2024-05-30 10:05:11', '2024-05-30 10:05:29');
INSERT INTO `t_dict_data` VALUES (16, 'system_task_error_policy', '执行一次', '2', '定时任务错误策略执行一次', 2, '2024-05-30 10:05:53', '2024-05-30 10:05:53');
INSERT INTO `t_dict_data` VALUES (17, 'system_task_error_policy', '放弃执行', '3', '定时任务错误策略:放弃执行', 3, '2024-05-30 10:06:10', '2024-05-30 10:06:10');
INSERT INTO `t_dict_data` VALUES (18, 'message_comment_audit_status', '未审核', '0', '评论审核状态:未审核', 1, '2024-05-30 17:58:50', '2024-05-30 17:58:50');
INSERT INTO `t_dict_data` VALUES (19, 'message_comment_audit_status', '通过', '1', '评论审核状态:审核通过', 2, '2024-05-30 17:59:15', '2024-05-30 18:12:19');
INSERT INTO `t_dict_data` VALUES (20, 'message_comment_audit_status', '驳回', '2', '评论审核状态:驳回', 3, '2024-05-30 17:59:38', '2024-05-30 17:59:38');
INSERT INTO `t_dict_data` VALUES (21, 'friend_link_show_status', '申请', '0', '友链展示状态:申请', 1, '2024-06-03 10:07:37', '2024-06-03 10:07:37');
INSERT INTO `t_dict_data` VALUES (22, 'friend_link_show_status', '展示', '1', '友链展示状态:展示', 2, '2024-06-03 10:07:56', '2024-06-03 10:07:56');
INSERT INTO `t_dict_data` VALUES (23, 'friend_link_show_status', '不展示', '2', '友链展示状态:不展示', 3, '2024-06-03 10:08:14', '2024-06-03 10:08:14');
INSERT INTO `t_dict_data` VALUES (24, 'article_status', '草稿', '0', '文章状态:草稿', 1, '2024-06-08 16:55:06', '2024-06-08 17:01:23');
INSERT INTO `t_dict_data` VALUES (25, 'article_status', '发布', '1', '文章状态:发布', 2, '2024-06-08 16:55:23', '2024-06-08 17:01:24');
INSERT INTO `t_dict_data` VALUES (26, 'article_status', '下架', '2', '文章状态:下架', 3, '2024-06-08 16:55:40', '2024-06-08 17:01:26');
INSERT INTO `t_dict_data` VALUES (27, 'system_encrypt_key', '密钥', '4a8edf44de99531c', '数据密钥', 1, '2024-07-03 15:15:10', '2024-07-03 15:15:10');
INSERT INTO `t_dict_data` VALUES (28, 'comment_type', '文章', '1', '评论类型:文章', 1, '2024-08-02 16:29:36', '2024-08-02 16:30:08');
INSERT INTO `t_dict_data` VALUES (29, 'comment_type', '说说', '2', '评论类型:说说', 2, '2024-08-02 16:30:07', '2024-08-02 16:30:20');
INSERT INTO `t_dict_data` VALUES (30, 'comment_type', '友链', '3', '评论类型:友链', 3, '2024-08-02 16:30:18', '2024-08-02 16:30:26');

-- ----------------------------
-- Table structure for t_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_type`;
CREATE TABLE `t_dict_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型',
  `dict_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_dict_type`(`dict_type`) USING BTREE,
  INDEX `idx_sort`(`sort`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dict_type
-- ----------------------------
INSERT INTO `t_dict_type` VALUES (2, 'system_user_sex', '系统用户性别', '系统用户性别', 1, '2024-05-28 11:16:22', '2024-05-28 14:51:46');
INSERT INTO `t_dict_type` VALUES (4, 'system_number_status', '系统数字状态', '系统数字状态', 2, '2024-05-28 16:47:09', '2024-05-28 16:47:09');
INSERT INTO `t_dict_type` VALUES (5, 'system_boolean_status', '系统布尔状态', '系统布尔状态', 3, '2024-05-28 16:47:57', '2024-05-28 16:47:57');
INSERT INTO `t_dict_type` VALUES (6, 'system_request_type', '系统请求方式', '系统请求方式', 4, '2024-05-28 18:20:54', '2024-05-28 18:20:54');
INSERT INTO `t_dict_type` VALUES (7, 'system_config_type', '系统配置类型', '系统配置类型', 5, '2024-05-30 09:50:35', '2024-05-30 09:50:35');
INSERT INTO `t_dict_type` VALUES (8, 'system_task_error_policy', '系统定时任务错误策略', '系统定时任务错误策略', 6, '2024-05-30 10:04:38', '2024-05-30 10:04:38');
INSERT INTO `t_dict_type` VALUES (9, 'message_comment_audit_status', '评论审核状态', '评论审核状态', 7, '2024-05-30 17:48:37', '2024-05-30 17:56:22');
INSERT INTO `t_dict_type` VALUES (10, 'friend_link_show_status', '友链展示状态', '友链展示状态', 8, '2024-06-03 10:06:55', '2024-06-03 10:06:55');
INSERT INTO `t_dict_type` VALUES (11, 'article_status', '文章状态', '文章状态', 9, '2024-06-08 16:54:43', '2024-06-08 17:00:16');
INSERT INTO `t_dict_type` VALUES (12, 'system_encrypt_key', '传输秘钥', '传输数据秘钥', 10, '2024-07-03 15:13:25', '2024-07-03 15:13:25');
INSERT INTO `t_dict_type` VALUES (13, 'comment_type', '评论类型', '评论类型', 11, '2024-08-02 16:28:49', '2024-08-02 16:28:47');

-- ----------------------------
-- Table structure for t_feed_back
-- ----------------------------
DROP TABLE IF EXISTS `t_feed_back`;
CREATE TABLE `t_feed_back`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细内容',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `back_type` tinyint(1) NOT NULL COMMENT '反馈类型 0:需求 1：缺陷',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态 0未解决 1解决',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户反馈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_feed_back
-- ----------------------------
INSERT INTO `t_feed_back` VALUES (1, 1, '这是一个bug', '这是一个bug', 'https://img2.baidu.com/it/u=2777280900,1082757912&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=500', 1, 0, '182.47.184.217', '亚洲, 中国, 日照', 'Chrome 123-Windows 10', '2024-05-31 14:22:57', '2024-10-29 17:42:59');
INSERT INTO `t_feed_back` VALUES (2, 1, '这是一个需求', '这是一个需求', 'https://img2.baidu.com/it/u=2777280900,1082757912&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=500', 0, 1, '182.47.184.217', '亚洲, 中国, 日照', 'Chrome 123-Windows 10', '2024-05-31 14:22:57', '2024-10-29 17:43:00');
INSERT INTO `t_feed_back` VALUES (4, 17, '这是测试反馈', '这是测试反馈', 'https://img2.baidu.com/it/u=2777280900,1082757912&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=500', 0, 0, '192.168.56.1', '全球', 'Chrome 122-Windows 10', '2024-09-30 17:29:22', '2024-10-29 17:43:01');

-- ----------------------------
-- Table structure for t_friend_link
-- ----------------------------
DROP TABLE IF EXISTS `t_friend_link`;
CREATE TABLE `t_friend_link`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站名称',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站地址',
  `image` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站logo',
  `info` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站描述',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否展示:0申请,1展示,2不展示',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '友情链接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_friend_link
-- ----------------------------
INSERT INTO `t_friend_link` VALUES (1, '鸢尾博客', 'https://www.lsar.icu', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '这是鸢尾博客', '1444073716@qq.com', 1, 1, '2024-06-03 09:46:32', '2024-10-29 18:01:49');
INSERT INTO `t_friend_link` VALUES (2, '百度', 'https://www.baidu.com/', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '这是百度', 'baidu@qq.com', 1, 2, '2024-06-03 09:47:14', '2024-10-29 18:01:49');
INSERT INTO `t_friend_link` VALUES (3, '腾讯', 'www.qq.com', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '腾讯网', 'tencent@qq.com', 1, 31, '2024-06-03 10:47:29', '2024-10-29 18:01:49');

-- ----------------------------
-- Table structure for t_gateway_config
-- ----------------------------
DROP TABLE IF EXISTS `t_gateway_config`;
CREATE TABLE `t_gateway_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置名',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置路径',
  `file_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
  `position` bigint(20) NOT NULL DEFAULT 0 COMMENT '读取位置',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_position`(`position`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网关配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_gateway_config
-- ----------------------------
INSERT INTO `t_gateway_config` VALUES (1, 'Nginx日志配置', 'C:\\Users\\admin\\Desktop\\fsdownload\\access.log', 'access.log', 33278, '2024-06-13 14:23:05', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_config` VALUES (2, 'Nginx错误日志配置', 'C:\\Users\\admin\\Desktop\\fsdownload\\error.log', 'error.log', 0, '2024-06-13 14:24:20', '2024-06-13 14:24:20');

-- ----------------------------
-- Table structure for t_gateway_log
-- ----------------------------
DROP TABLE IF EXISTS `t_gateway_log`;
CREATE TABLE `t_gateway_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `request_end_time` datetime(0) NULL DEFAULT NULL COMMENT '请求完成时间',
  `status_code` int(10) NULL DEFAULT NULL COMMENT '响应状态码',
  `request_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `server_protocol` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '协议及版本',
  `server_port` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '端口号',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `args` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `bytes_sent` bigint(20) NULL DEFAULT NULL COMMENT '响应字节大小',
  `request_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求花费时间(秒)',
  `http_user_agent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端信息',
  `latitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `longitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经度',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_request_method`(`request_method`) USING BTREE,
  INDEX `idx_status_code`(`status_code`) USING BTREE,
  INDEX `idx_server_port`(`server_port`) USING BTREE,
  INDEX `idx_uri`(`uri`) USING BTREE,
  INDEX `idx_ip_request_end_time`(`ip`, `request_end_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1193 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网关日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_gateway_log
-- ----------------------------
INSERT INTO `t_gateway_log` VALUES (660, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/actuator/globalinfo', '-', 802, '0.014', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:20', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (661, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/apis/content.halo.run/v1alpha1/tags', 'page=0&size=0&sort=metadata.creationTimestamp%2Cdesc', 993, '0.018', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:20', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (662, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/apis/content.halo.run/v1alpha1/categories', 'page=0&size=0&sort=metadata.creationTimestamp%2Cdesc', 1126, '0.023', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:20', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (663, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/apis/api.console.halo.run/v1alpha1/posts', 'keyword=&labelSelector=content.halo.run%2Fdeleted%3Dfalse&page=1&size=20', 4179, '0.031', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:20', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (664, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/api/v1alpha1/annotationsettings', 'labelSelector=halo.run%2Ftarget-ref%3Dcontent.halo.run%2FPost', 2781, '0.026', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:20', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (665, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/api/v1alpha1/users', 'fieldSelector=name%21%3DanonymousUser', 2570, '0.025', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:20', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (666, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/api/v1alpha1/annotationsettings', 'labelSelector=halo.run%2Ftarget-ref%3Dcontent.halo.run%2FSinglePage', 2232, '0.017', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:20', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (667, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/api/v1alpha1/users', 'fieldSelector=name%21%3DanonymousUser', 2570, '0.015', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:20', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (668, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/apis/api.console.halo.run/v1alpha1/singlepages', 'keyword=&labelSelector=content.halo.run%2Fdeleted%3Dfalse&page=1&size=20', 2652, '0.020', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:20', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (669, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/actuator/health', '-', 538, '0.010', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (670, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/api/v1alpha1/annotationsettings', 'labelSelector=halo.run%2Ftarget-ref%3Dcontent.halo.run%2FSinglePage', 2232, '0.011', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (671, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/apis/content.halo.run/v1alpha1/singlepages/373a5f79-f44f-441a-9df1-85a4f553ece8', '-', 2226, '0.010', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (672, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/apis/content.halo.run/v1alpha1/snapshots/a6e8c07b-7ab7-457c-b94e-984ccb3cd3bf', '-', 1005, '0.013', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (673, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/api/v1alpha1/annotationsettings', 'labelSelector=halo.run%2Ftarget-ref%3Dcontent.halo.run%2FSinglePage', 2232, '0.011', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (674, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/apis/api.console.halo.run/v1alpha1/singlepages/373a5f79-f44f-441a-9df1-85a4f553ece8/head-content', '-', 1206, '0.018', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (675, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/apis/content.halo.run/v1alpha1/singlepages/373a5f79-f44f-441a-9df1-85a4f553ece8', '-', 2226, '0.010', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (676, '171.221.139.251', '2024-07-13 20:32:14', 200, 'PUT', 'HTTP/1.1', '443', '/apis/content.halo.run/v1alpha1/singlepages/373a5f79-f44f-441a-9df1-85a4f553ece8', '-', 2318, '0.026', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (677, '171.221.139.251', '2024-07-13 20:32:14', 200, 'PUT', 'HTTP/1.1', '443', '/apis/api.console.halo.run/v1alpha1/singlepages/373a5f79-f44f-441a-9df1-85a4f553ece8/content', '-', 2318, '0.044', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (678, '171.221.139.251', '2024-07-13 20:32:14', 200, 'PUT', 'HTTP/1.1', '443', '/apis/api.console.halo.run/v1alpha1/singlepages/373a5f79-f44f-441a-9df1-85a4f553ece8/publish', '-', 2320, '0.138', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (679, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/api/v1alpha1/annotationsettings', 'labelSelector=halo.run%2Ftarget-ref%3Dcontent.halo.run%2FSinglePage', 2232, '0.010', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (680, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/api/v1alpha1/users', 'fieldSelector=name%21%3DanonymousUser', 2570, '0.018', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (681, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/apis/api.console.halo.run/v1alpha1/singlepages', 'keyword=&labelSelector=content.halo.run%2Fdeleted%3Dfalse&page=1&size=20', 2654, '0.022', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (682, '220.196.160.73', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/', '-', 21669, '0.062', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (683, '175.24.214.93', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/plugins/PluginSearchWidget/assets/static/search-widget.iife.js', '-', 94660, '0.066', 'Go-http-client/1.1', '31.23037', '121.4737', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (684, '175.24.214.93', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/js/utils.min.js', 'mew=1.3.0', 2599, '0.005', 'Go-http-client/1.1', '31.23037', '121.4737', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (685, '220.196.160.51', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/', '-', 21669, '0.058', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (686, '220.196.160.51', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/css/theme.min.css', 'mew=1.3.0', 2249, '0.004', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (687, '220.196.160.61', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/lib/remixicon@3.5.0/remixicon.min.css', '-', 19340, '0.010', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (688, '220.196.160.45', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/css/cursor.min.css', 'mew=1.3.0', 834, '0.005', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (689, '220.196.160.151', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/css/mew-custom.min.css', 'mew=1.3.0', 10897, '0.007', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:21', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (690, '220.196.160.146', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/css/style.min.css', 'mew=1.3.0', 22090, '0.009', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', NULL, NULL, '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (691, '220.196.160.96', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/lib/qmsg/qmsg.min.css', '-', 1244, '0.003', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (692, '220.196.160.51', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/lib/jquery@3.5.1/jquery.min.js', '-', 34558, '0.043', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (693, '220.196.160.146', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/js/utils.min.js', 'mew=1.3.0', 2594, '0.005', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', NULL, NULL, '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (694, '220.196.160.51', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/js/btoc.min.js', 'mew=1.3.0', 1701, '0.004', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (695, '220.196.160.151', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/js/dprogress.min.js', 'mew=1.3.0', 1666, '0.004', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (696, '220.196.160.61', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/js/common.min.js', 'mew=1.3.0', 4126, '0.004', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (697, '220.196.160.96', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/js/mew-custom.min.js', 'mew=1.3.0', 4399, '0.016', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (698, '220.196.160.51', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/upload/7f8bd2fc-488a-4460-93ce-9436e1435923.png', '-', 58961, '0.005', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (699, '220.196.160.45', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/plugins/PluginSearchWidget/assets/static/search-widget.iife.js', '-', 94660, '0.064', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (700, '220.196.160.151', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/lib/qmsg/qmsg.min.js', '-', 3357, '0.005', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (701, '220.196.160.61', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/cursor/rainbow_rain/normal.cur', '-', 4569, '0.005', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (702, '220.196.160.96', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/cursor/rainbow_rain/link.cur', '-', 4569, '0.005', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (703, '220.196.160.45', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/cursor/rainbow_rain/texto.cur', '-', 4569, '0.004', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (704, '220.196.160.61', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/js/spark-input.min.js', 'mew=1.3.0', 1525, '0.004', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (705, '220.196.160.151', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/plugins/PluginSearchWidget/assets/static/style.css', '-', 11732, '0.007', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (706, '220.196.160.96', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/lib/remixicon@3.5.0/remixicon.woff2', 't=1690730386070', 144005, '1.040', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (707, '220.196.160.146', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/upload/ç¼ç¨åµ.png', '-', 1121169, '3.018', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', NULL, NULL, '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (708, '220.196.160.51', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/upload/æä¸ æ  å¥³å­© É¡ èå½± å¯ç¾4kçµèå£çº¸_å½¼å²¸å¾ç½.jpg', '-', 1697342, '5.937', 'Mozilla/5.0 (compatible; CensysInspect/1.1; +https://about.censys.io/)', '31.23037', '121.4737', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (709, '159.75.199.224', '2024-07-13 20:32:14', 404, 'GET', 'HTTP/1.1', '80', '/geoserver/web/', '-', 413, '0.006', 'mozilla/5.0 (iphone; cpu iphone os 13_5_1 like mac os x) applewebkit/605.1.15 (khtml, like gecko) mobile/15e148 micromessenger/7.0.15(0x17000f2b) nettype/4g language/zh_cn miniprogram', '23.12908', '113.26436', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (710, '106.52.79.248', '2024-07-13 20:32:14', 404, 'GET', 'HTTP/1.1', '80', '/info.php', '-', 402, '0.005', 'mozilla/5.0 (iphone; cpu iphone os 13_5_1 like mac os x) applewebkit/605.1.15 (khtml, like gecko) mobile/15e148 micromessenger/7.0.15(0x17000f2b) nettype/4g language/zh_cn miniprogram', '23.12908', '113.26436', '2024-07-16 10:37:22', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (711, '185.224.128.43', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/', '-', 21445, '0.060', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36 Edg/90.0.818.46', '52.35', '4.9166', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (712, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/', '-', 21579, '0.062', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (713, '78.153.140.179', '2024-07-13 20:32:14', 404, 'GET', 'HTTP/1.1', '80', '/.env', '-', 394, '0.006', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36', '55.75', '37.6', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (714, '78.153.140.179', '2024-07-13 20:32:14', 400, '-', '-', '80', '-', '-', 295, '0.928', '-', '55.75', '37.6', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (715, '180.163.28.109', '2024-07-13 20:32:14', 404, 'GET', 'HTTP/1.1', '80', '/admin/index.php', '-', 416, '0.006', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36', '31.37482', '121.26621', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (716, '171.221.139.251', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/', '-', 21579, '0.065', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36', '30.57447', '103.92377', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (717, '180.163.28.180', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/', '-', 21446, '0.063', 'Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3; rv:11.0) like Gecko', '31.37482', '121.26621', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (718, '180.163.28.180', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/themes/theme-dream/assets/lib/jquery@3.5.1/jquery.min.js', '-', 89758, '5.620', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36', '31.37482', '121.26621', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (719, '180.163.28.180', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/plugins/PluginSearchWidget/assets/static/search-widget.iife.js', '-', 131072, '10.342', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) 			Chrome/55.0.2883.95 Safari/537.36', '31.37482', '121.26621', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (720, '180.163.28.180', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/themes/theme-dream/assets/js/utils.min.js', 'mew=1.3.0', 4415, '0.005', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0', '31.37482', '121.26621', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (721, '180.163.28.180', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/themes/theme-dream/assets/js/btoc.min.js', 'mew=1.3.0', 3094, '0.004', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.2141.400 QQBrowser/9.5.10219.400', '31.37482', '121.26621', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (722, '180.163.28.180', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/themes/theme-dream/assets/js/common.min.js', 'mew=1.3.0', 10370, '0.004', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.2141.400 QQBrowser/9.5.10219.400', '31.37482', '121.26621', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (723, '180.163.28.180', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/themes/theme-dream/assets/js/mew-custom.min.js', 'mew=1.3.0', 12354, '0.004', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.2141.400 QQBrowser/9.5.10219.400', '31.37482', '121.26621', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (724, '180.163.28.180', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/themes/theme-dream/assets/js/dprogress.min.js', 'mew=1.3.0', 1666, '0.004', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.2141.400 QQBrowser/9.5.10219.400', '31.37482', '121.26621', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (725, '180.163.28.180', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '443', '/themes/theme-dream/assets/lib/qmsg/qmsg.min.js', '-', 9624, '0.005', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) 			Chrome/55.0.2883.95 Safari/537.36', '31.37482', '121.26621', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (726, '167.99.91.44', '2024-07-13 20:32:14', 400, '-', '-', '80', '-', '-', 295, '0.235', '-', '51.5', '-0.0833', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (727, '192.155.90.220', '2024-07-13 20:32:14', 400, '-', '-', '80', '-', '-', 295, '0.227', '-', '16.8', '96.15', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (728, '220.196.160.146', '2024-07-13 20:32:14', 404, 'GET', 'HTTP/1.1', '80', '/vendor/phpunit/phpunit/src/Util/PHP/eval-stdin.php', '-', 23365, '0.082', 'python-requests/2.25.1', NULL, NULL, '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (729, '49.234.25.245', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/plugins/PluginSearchWidget/assets/static/search-widget.iife.js', '-', 94660, '0.069', 'Go-http-client/1.1', '31.23037', '121.4737', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (730, '49.234.25.245', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/js/utils.min.js', 'mew=1.3.0', 2599, '0.005', 'Go-http-client/1.1', '31.23037', '121.4737', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (731, '180.101.245.248', '2024-07-13 20:32:14', 404, 'GET', 'HTTP/1.1', '80', '/vendor/phpunit/phpunit/src/Util/PHP/eval-stdin.php', '-', 23365, '0.063', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (732, '180.101.244.16', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/css/theme.min.css', 'mew=1.3.0', 2249, '0.008', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:23', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (733, '180.101.245.250', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/css/style.min.css', 'mew=1.3.0', 22069, '0.013', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (734, '180.101.245.253', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/lib/remixicon@3.5.0/remixicon.min.css', '-', 19320, '0.011', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (735, '180.101.244.15', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/css/post.min.css', 'mew=1.3.0', 1873, '0.006', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (736, '180.101.245.248', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/css/mew-custom.min.css', 'mew=1.3.0', 10897, '0.012', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (737, '180.101.245.249', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/lib/highlightjs@11.5.1/styles/atom-one-light.min.css', '-', 1122, '0.005', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (738, '180.101.244.15', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/lib/qmsg/qmsg.min.css', '-', 1239, '0.005', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (739, '180.101.245.248', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/css/cursor.min.css', 'mew=1.3.0', 834, '0.003', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (740, '180.101.244.16', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/lib/fancybox@5.3.7/jquery.fancybox.min.css', '-', 3759, '0.023', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (741, '180.101.245.249', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/lib/jquery@3.5.1/jquery.min.js', '-', 34558, '0.052', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (742, '180.101.244.16', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/js/utils.min.js', 'mew=1.3.0', 2599, '0.005', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (743, '180.101.245.249', '2024-07-13 20:32:14', 200, 'GET', 'HTTP/1.1', '80', '/themes/theme-dream/assets/js/btoc.min.js', 'mew=1.3.0', 1701, '0.005', 'python-requests/2.25.1', '32.06071', '118.76295', '2024-07-16 10:37:24', '2024-07-16 10:39:05');
INSERT INTO `t_gateway_log` VALUES (1190, '182.148.48.198', '2024-07-24 10:31:06', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/message/sse/close', '-', 366, '0.017', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', '30.64242', '104.04311', '2024-07-24 13:37:43', '2024-07-24 13:37:43');
INSERT INTO `t_gateway_log` VALUES (1191, '182.148.48.198', '2024-07-24 10:31:06', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/captcha/get', '-', 109847, '0.071', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', '30.64242', '104.04311', '2024-07-24 13:37:43', '2024-07-24 13:37:43');
INSERT INTO `t_gateway_log` VALUES (1192, '182.148.48.198', '2024-07-24 10:31:16', 502, 'GET', 'HTTP/1.1', '443', '/iris/system/message/sse/connect', '-', 727, '31.478', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', '30.64242', '104.04311', '2024-07-24 13:37:43', '2024-07-24 13:37:43');
INSERT INTO `t_gateway_log` VALUES (1193, '182.148.48.198', '2024-07-24 10:25:31', 200, 'GET', 'HTTP/1.1', '443', '/index.html', '-', 3603, '0.000', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1194, '182.148.48.198', '2024-07-24 10:25:31', 200, 'GET', 'HTTP/1.1', '443', '/index.html', '', 3603, '0.000', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1195, '182.148.48.198', '2024-07-24 10:25:31', 200, 'GET', 'HTTP/1.1', '443', '/index.html', '', 3603, '0.000', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1196, '182.148.48.198', '2024-07-24 10:25:33', 200, 'GET', 'HTTP/1.1', '443', '/assets/normal-DnarbRT3.cur', '-', 4559, '0.000', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1197, '182.148.48.198', '2024-07-24 10:25:42', 502, 'GET', 'HTTP/1.1', '443', '/iris/system/dict/data/select', '-', 727, '0.004', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1198, '182.148.48.198', '2024-07-24 10:25:42', 200, 'GET', 'HTTP/1.1', '443', '/assets/texto-Doz4TW7Q.cur', '-', 4559, '0.000', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1199, '182.148.48.198', '2024-07-24 10:25:46', 200, 'GET', 'HTTP/1.1', '443', '/assets/2-C4e6RK9j.woff2', '-', 3621580, '12.909', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1200, '182.148.48.198', '2024-07-24 10:25:58', 304, 'GET', 'HTTP/1.1', '443', '/index.html', '-', 198, '0.000', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1201, '182.148.48.198', '2024-07-24 10:25:59', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/dict/data/select', '-', 366, '1.058', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1202, '182.148.48.198', '2024-07-24 10:25:59', 200, 'GET', 'HTTP/1.1', '443', '/assets/link-C-w38oq2.cur', '-', 4559, '0.000', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1203, '182.148.48.198', '2024-07-24 10:26:03', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/captcha/get', '-', 151332, '1.334', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:00', '2024-10-29 18:10:00');
INSERT INTO `t_gateway_log` VALUES (1204, '220.196.160.144', '2024-07-24 10:26:05', 200, 'GET', 'HTTP/1.1', '443', '/index.html', '-', 3603, '0.000', 'Mozilla/5.0 (iPhone; CPU iPhone os 15_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.4 Mobile/11D257 Safari/604.1', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1205, '106.55.202.186', '2024-07-24 10:26:06', 200, 'GET', 'HTTP/1.1', '443', '/index.html', '', 3603, '0.000', 'Go-http-client/1.1', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1206, '180.101.244.14', '2024-07-24 10:26:09', 200, 'GET', 'HTTP/1.1', '443', '/index.html', '-', 3603, '0.000', 'Mozilla/5.0 (iPhone; CPU iPhone OS 13_7 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1 Mobile/15E148 Safari/604.1', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1207, '180.101.244.14', '2024-07-24 10:26:09', 200, 'GET', 'HTTP/1.1', '443', '/index.html', '', 3603, '0.000', 'Mozilla/5.0 (iPhone; CPU iPhone OS 13_7 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1 Mobile/15E148 Safari/604.1', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1208, '180.101.245.251', '2024-07-24 10:26:09', 200, 'GET', 'HTTP/1.1', '443', '/index.html', '', 3603, '0.000', 'Mozilla/5.0 (iPhone; CPU iPhone OS 13_7 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1 Mobile/15E148 Safari/604.1', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1209, '180.101.244.14', '2024-07-24 10:26:10', 200, 'GET', 'HTTP/1.1', '443', '/assets/normal-DnarbRT3.cur', '-', 4559, '0.000', 'Mozilla/5.0 (iPhone; CPU iPhone OS 13_7 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1 Mobile/15E148 Safari/604.1', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1210, '182.148.48.198', '2024-07-24 10:26:14', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/captcha/check', '-', 871, '0.131', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1211, '180.101.245.251', '2024-07-24 10:26:14', 200, 'GET', 'HTTP/1.1', '443', '/assets/2-C4e6RK9j.woff2', '-', 720896, '4.771', 'Mozilla/5.0 (iPhone; CPU iPhone OS 13_7 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1 Mobile/15E148 Safari/604.1', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1212, '182.148.48.198', '2024-07-24 10:26:16', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/captcha/get', '-', 109275, '0.326', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1213, '182.148.48.198', '2024-07-24 10:26:17', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/login/login', '-', 771, '1.636', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1214, '182.148.48.198', '2024-07-24 10:26:17', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/dict/data/select', '-', 4258, '0.109', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1215, '182.148.48.198', '2024-07-24 10:26:18', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/permission/get-permission-info', '-', 8614, '0.370', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1216, '182.148.48.198', '2024-07-24 10:26:28', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/gateway/log/ip', '-', 330, '0.122', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1217, '182.148.48.198', '2024-07-24 10:26:28', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/common/get/ip', '-', 526, '0.152', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1218, '182.148.48.198', '2024-07-24 10:26:28', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/home/statistics', '-', 585, '0.219', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1219, '182.148.48.198', '2024-07-24 10:26:29', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/home/data/panel', '-', 4622, '0.139', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1220, '182.148.48.198', '2024-07-24 10:26:29', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/common/get/weather', 'cityCode=', 4084, '1.174', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1221, '182.148.48.198', '2024-07-24 10:26:45', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/article/list', '-', 4792, '0.187', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1222, '182.148.48.198', '2024-07-24 10:26:45', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/article/category/select', '-', 1183, '0.064', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1223, '182.148.48.198', '2024-07-24 10:26:45', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/article/tag/select', '-', 1237, '0.040', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1224, '182.148.48.198', '2024-07-24 10:26:47', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/article/category/list', '-', 1629, '0.066', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1225, '182.148.48.198', '2024-07-24 10:26:49', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/article/tag/list', '-', 1686, '0.045', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1226, '182.148.48.198', '2024-07-24 10:26:53', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/leave/message/list', '-', 1116, '0.045', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1227, '182.148.48.198', '2024-07-24 10:26:54', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/feed/back/list', '-', 1164, '0.112', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1228, '182.148.48.198', '2024-07-24 10:26:57', 200, 'POST', 'HTTP/1.1', '443', '/iris/oss/file/list', '-', 2638, '0.056', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1229, '182.148.48.198', '2024-07-24 10:26:58', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/config/get/code/value', 'paramCode=SYS_OSS_CONFIG_KEY', 1388, '0.040', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1230, '182.148.48.198', '2024-07-24 10:27:28', 504, 'GET', 'HTTP/1.1', '443', '/iris/system/message/sse/connect', '-', 742, '60.042', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1231, '182.148.48.198', '2024-07-24 10:27:39', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/config/update/code/value', 'paramCode=SYS_OSS_CONFIG_KEY&paramValue=%7B%22aliyunAccessKeyId%22%3A%22LTAI5tLJKshMDHiGVxeatCaR%22%2C%22aliyunAccessKeySecret%22%3A%22IC5623RxiFT1tqOrbndYwvKTnDK3vL%22%2C%22aliyunBucketName%22%3A%22zuiwanjia-images%22%2C%22aliyunDomain%22%3A%22https%3A%2F%2Fpzdsoss.pzds.com%22%2C%22aliyunEndPoint%22%3A%22https%3A%2F%2Foss-cn-chengdu.aliyuncs.com%22%2C%22localDomain%22%3A%22D%3A%2FuploadFile%2F%22%2C%22localPath%22%3A%22D%3A%2FuploadFile%2F%22%2C%22qcloudAppId%22%3A1300679084%2C%22qcloudBucketName%22%3A%22echo20%22%2C%22qcloudDomain%22%3A%22https%3A%2F%2Fecho20-1300679084.cos.ap-chengdu.myqcloud.com%22%2C%22qcloudRegion%22%3A%22ap-chengdu%22%2C%22qcloudSecretId%22%3A%22AKIDwkXCdpdKp57wWs0tYyAKFuKUTHuQlvqZ%22%2C%22qcloudSecretKey%22%3A%22hDWbdvvqgKmz4zg9VhgPxJQIyJRTM2Xp%22%2C%22qiniuAccessKey%22%3A%223TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS%22%2C%22qiniuBucketName%22%3A%22ruoyi-vue-pro%22%2C%22qiniuDomain%22%3A%22http%3A%2F%2Ftest.yudao.iocoder.cn%22%2C%22qiniuSecretKey%22%3A%22wd0tbVBYlp0S-ihA8Qg2hPLncoP83wyrIq24OZuY%22%2C%22type%22%3A2%2C%22directoryList%22%3A%22goods%22%2C%22minioEndPoint%22%3A%22http%3A%2F%2F192.168.56.10%3A9090%22%2C%22minioAccessKey%22%3A%221ONPOssH5F2HGzRCiitp%22%2C%22minioSecretKey%22%3A%22ewhKA8Axw5RJWGECoCPAZI2NVIoFu6cViNhhcKcH%22%2C%22minioBucketName%22%3A%22test-bucket%22%7D', 332, '0.153', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1232, '182.148.48.198', '2024-07-24 10:27:40', 200, 'POST', 'HTTP/1.1', '443', '/iris/oss/file/list', '-', 2638, '0.051', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1233, '182.148.48.198', '2024-07-24 10:27:43', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/monitor/systemInfo', '-', 1175, '1.484', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1234, '20.83.185.23', '2024-07-24 10:27:55', 403, 'GET', 'HTTP/1.1', '80', '/.env', '-', 319, '0.000', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:77.0) Gecko/20100101 Firefox/77.0', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1235, '182.148.48.198', '2024-07-24 10:27:55', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/monitor/cacheInfo', '-', 6698, '0.114', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1236, '182.148.48.198', '2024-07-24 10:27:55', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/monitor/cacheInfo', '-', 6698, '0.021', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1237, '182.148.48.198', '2024-07-24 10:27:55', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/monitor/cacheInfo', '-', 6698, '0.029', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1238, '182.148.48.198', '2024-07-24 10:28:00', 302, 'GET', 'HTTP/1.1', '443', '/iris/druid/index.html', '-', 280, '0.007', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1239, '182.148.48.198', '2024-07-24 10:28:00', 200, 'GET', 'HTTP/1.1', '443', '/irisdruid/login.html', '-', 3691, '0.006', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1240, '182.148.48.198', '2024-07-24 10:28:08', 200, 'POST', 'HTTP/1.1', '443', '/irisdruid/submitLogin', '-', 404, '0.020', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1241, '182.148.48.198', '2024-07-24 10:28:08', 302, 'GET', 'HTTP/1.1', '443', '/irisdruid/index.html', '-', 280, '0.002', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1242, '182.148.48.198', '2024-07-24 10:28:08', 200, 'GET', 'HTTP/1.1', '443', '/irisdruid/login.html', '-', 3691, '0.004', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1243, '182.148.48.198', '2024-07-24 10:28:11', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/monitor/serviceInfo', '-', 632, '0.137', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1244, '182.148.48.198', '2024-07-24 10:28:19', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/log/list', '-', 6517, '0.065', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1245, '182.148.48.198', '2024-07-24 10:28:21', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/log/list', '-', 86780, '0.114', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1246, '182.148.48.198', '2024-07-24 10:28:26', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/gateway/config/list', '-', 842, '0.067', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1247, '182.148.48.198', '2024-07-24 10:28:31', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/gateway/log/list', '-', 4732, '0.057', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1248, '182.148.48.198', '2024-07-24 10:28:33', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/gateway/log/list', '-', 5442, '0.059', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1249, '182.148.48.198', '2024-07-24 10:28:34', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/gateway/log/list', '-', 4633, '0.025', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1250, '182.148.48.198', '2024-07-24 10:28:39', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/gateway/log/list', '-', 4726, '0.033', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1251, '182.148.48.198', '2024-07-24 10:28:42', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/user/list', '-', 1931, '0.052', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1252, '182.148.48.198', '2024-07-24 10:28:44', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/role/list', '-', 1154, '0.082', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1253, '182.148.48.198', '2024-07-24 10:28:46', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/menu/list', '-', 30924, '0.116', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1254, '182.148.48.198', '2024-07-24 10:28:49', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/job/list', '-', 1725, '0.066', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1255, '182.148.48.198', '2024-07-24 10:28:55', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/job/run', 'id=22', 360, '0.111', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1256, '182.148.48.198', '2024-07-24 10:28:58', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/job/change/status', 'id=22', 332, '0.126', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1257, '182.148.48.198', '2024-07-24 10:28:58', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/job/list', '-', 1725, '0.047', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1258, '182.148.48.198', '2024-07-24 10:29:02', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/job/run', 'id=22', 350, '0.085', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1259, '182.148.48.198', '2024-07-24 10:29:04', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/job/log/list', '-', 4111, '0.068', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1260, '182.148.48.198', '2024-07-24 10:29:09', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/job/log/info', 'id=3381', 692, '0.010', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1261, '182.148.48.198', '2024-07-24 10:29:32', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/role/delete', '-', 424, '0.102', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1262, '182.148.48.198', '2024-07-24 10:29:32', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/role/list', '-', 1024, '0.027', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1263, '182.148.48.198', '2024-07-24 10:29:35', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/menu/select/list', '-', 5671, '0.042', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1264, '182.148.48.198', '2024-07-24 10:29:35', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/permission/list-role-menus', 'roleId=4', 367, '0.032', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1265, '182.148.48.198', '2024-07-24 10:29:42', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/role/delete', '-', 424, '0.078', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1266, '182.148.48.198', '2024-07-24 10:29:42', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/role/list', '-', 891, '0.024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1267, '182.148.48.198', '2024-07-24 10:29:44', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/config/list', '-', 1712, '0.068', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1268, '182.148.48.198', '2024-07-24 10:29:51', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/dict/type/list', '-', 2319, '0.061', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1269, '182.148.48.198', '2024-07-24 10:30:25', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/login/logout', '-', 431, '0.047', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1270, '182.148.48.198', '2024-07-24 10:30:26', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/message/sse/close', '-', 366, '0.010', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1271, '182.148.48.198', '2024-07-24 10:30:26', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/captcha/get', '-', 161984, '0.321', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1272, '182.148.48.198', '2024-07-24 10:30:43', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/captcha/check', '-', 871, '0.072', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1273, '182.148.48.198', '2024-07-24 10:30:44', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/login/login', '-', 771, '0.094', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1274, '182.148.48.198', '2024-07-24 10:30:44', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/permission/get-permission-info', '-', 8621, '0.092', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1275, '182.148.48.198', '2024-07-24 10:30:44', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/common/get/ip', '-', 526, '0.022', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1276, '182.148.48.198', '2024-07-24 10:30:44', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/gateway/log/ip', '-', 330, '0.024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1277, '182.148.48.198', '2024-07-24 10:30:44', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/home/statistics', '-', 585, '0.061', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1278, '182.148.48.198', '2024-07-24 10:30:44', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/home/data/panel', '-', 4616, '0.063', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1279, '182.148.48.198', '2024-07-24 10:30:44', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/common/get/weather', 'cityCode=', 4084, '0.156', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1280, '182.148.48.198', '2024-07-24 10:30:45', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/captcha/get', '-', 181556, '0.362', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1281, '124.165.82.114', '2024-07-24 10:30:52', 403, 'GET', 'HTTP/1.1', '80', '/liveplay-kk.rtxapp.com/live/program/live/cctv1hd8m/8000000/mnf.m3u8', '-', 289, '0.000', 'okhttp/3.15', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1282, '124.165.82.114', '2024-07-24 10:30:52', 403, 'GET', 'HTTP/1.1', '80', '/sxbuv52.live.bestvcdn.com.cn/live/program/live/cctv1hd8m/8000000/2024072323/172174700.ts', '-', 289, '0.000', 'okhttp/3.15', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1283, '182.148.48.198', '2024-07-24 10:30:57', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/article/list', '-', 4786, '0.044', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:01', '2024-10-29 18:10:01');
INSERT INTO `t_gateway_log` VALUES (1284, '182.148.48.198', '2024-07-24 10:30:57', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/article/category/select', '-', 1183, '0.018', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:02', '2024-10-29 18:10:02');
INSERT INTO `t_gateway_log` VALUES (1285, '182.148.48.198', '2024-07-24 10:30:57', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/article/tag/select', '-', 1237, '0.013', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:02', '2024-10-29 18:10:02');
INSERT INTO `t_gateway_log` VALUES (1286, '182.148.48.198', '2024-07-24 10:31:05', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/login/logout', '-', 431, '0.017', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:02', '2024-10-29 18:10:02');
INSERT INTO `t_gateway_log` VALUES (1287, '182.148.48.198', '2024-07-24 10:31:06', 200, 'GET', 'HTTP/1.1', '443', '/iris/system/message/sse/close', '-', 366, '0.017', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:02', '2024-10-29 18:10:02');
INSERT INTO `t_gateway_log` VALUES (1288, '182.148.48.198', '2024-07-24 10:31:06', 200, 'POST', 'HTTP/1.1', '443', '/iris/system/captcha/get', '-', 109847, '0.071', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:02', '2024-10-29 18:10:02');
INSERT INTO `t_gateway_log` VALUES (1289, '182.148.48.198', '2024-07-24 10:31:16', 502, 'GET', 'HTTP/1.1', '443', '/iris/system/message/sse/connect', '-', 727, '31.478', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', NULL, NULL, '2024-10-29 18:10:02', '2024-10-29 18:10:02');

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `goods_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `status` tinyint(3) NULL DEFAULT NULL COMMENT '商品状态,0关闭,1开启',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES (1, 'GD123', '别墅一套', 999.00, 1, '2024-05-27 17:35:08', '2024-05-27 17:35:08');
INSERT INTO `t_goods` VALUES (2, 'F123', '房子一套', 888.00, 0, '2024-05-26 17:36:16', '2024-05-27 17:37:09');

-- ----------------------------
-- Table structure for t_leave_message
-- ----------------------------
DROP TABLE IF EXISTS `t_leave_message`;
CREATE TABLE `t_leave_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态:0未审核,1审核通过,2驳回',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户留言表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_leave_message
-- ----------------------------
INSERT INTO `t_leave_message` VALUES (1, '这个擦大阿达是的', 'admin', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '182.47.184.217', '亚洲, 中国, 日照', 'Chrome 123-Windows 10', 1, '2024-05-30 13:44:02', '2024-10-29 18:01:25');
INSERT INTO `t_leave_message` VALUES (3, '干哈森岛帆高山东高速', '噶施工方撒', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '182.47.184.217', '亚洲, 中国, 日照', 'Chrome 123-Windows 10', 1, '2024-05-31 13:44:02', '2024-10-29 18:01:25');
INSERT INTO `t_leave_message` VALUES (4, '啊实打实大', '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '192.168.56.1', '亚洲, 中国, 日照', 'Chrome 122-Windows 10', 1, '2024-09-13 18:11:23', '2024-10-29 18:01:25');
INSERT INTO `t_leave_message` VALUES (5, '法发顺丰', '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '192.168.56.1', '亚洲, 中国, 日照', 'Chrome 122-Windows 10', 1, '2024-09-13 18:11:27', '2024-10-29 18:01:25');
INSERT INTO `t_leave_message` VALUES (6, '法师法师', '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '192.168.56.1', '亚洲, 中国, 日照', 'Chrome 122-Windows 10', 1, '2024-09-13 18:12:01', '2024-10-29 18:01:25');
INSERT INTO `t_leave_message` VALUES (7, '发飒飒飒', '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '192.168.56.1', '亚洲, 中国, 日照', 'Chrome 122-Windows 10', 1, '2024-09-13 18:13:11', '2024-10-29 18:01:25');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限标识',
  `type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '权限级别:1目录,2菜单,3按钮',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父ID',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '菜单状态:0禁用1启用',
  `visible` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可见',
  `keep_alive` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否缓存',
  `always_show` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否总是显示',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, '系统管理', '', 1, 10, 0, '/system', 'ep:tools', NULL, NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 17:09:54');
INSERT INTO `t_menu` VALUES (2, '服务监控', '', 1, 20, 0, '/server', 'ep:monitor', NULL, NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-23 13:44:44');
INSERT INTO `t_menu` VALUES (3, '用户管理', 'system:user:list', 2, 1, 1, 'user', 'ep:avatar', 'system/user/index', 'SystemUser', 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:00:30');
INSERT INTO `t_menu` VALUES (4, '角色管理', '', 2, 2, 1, 'role', 'ep:user', 'system/role/index', 'SystemRole', 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:00:47');
INSERT INTO `t_menu` VALUES (5, '菜单管理', '', 2, 3, 1, 'menu', 'ep:menu', 'system/menu/index', 'SystemMenu', 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:01:10');
INSERT INTO `t_menu` VALUES (6, '字典管理', '', 2, 5, 1, 'dict', 'ep:collection', 'system/dict/index', 'DictType', 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:02:05');
INSERT INTO `t_menu` VALUES (7, '系统配置', '', 2, 4, 1, 'config', 'ep:connection', 'system/config/index', 'SystemConfig', 1, 1, 1, 1, '', '2024-05-25 13:28:41', '', '2024-05-30 15:03:02');
INSERT INTO `t_menu` VALUES (8, '用户查询', 'system:user:query', 3, 1, 3, '', '#', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '', '2024-05-30 15:03:29');
INSERT INTO `t_menu` VALUES (9, '用户新增', 'system:user:save', 3, 2, 3, '', '', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-06-11 15:12:24');
INSERT INTO `t_menu` VALUES (10, '用户修改', 'system:user:update', 3, 3, 3, '', '', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:03:32');
INSERT INTO `t_menu` VALUES (11, '用户删除', 'system:user:delete', 3, 4, 3, '', '', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:03:34');
INSERT INTO `t_menu` VALUES (12, '设置用户角色', 'system:permission:assign-user-role', 3, 8, 4, '', '', '', NULL, 1, 1, 1, 1, '', '2021-01-07 10:23:28', '', '2024-05-30 15:03:35');
INSERT INTO `t_menu` VALUES (13, '重置密码', 'system:user:update-password', 3, 7, 3, '', '', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:03:37');
INSERT INTO `t_menu` VALUES (14, '角色查询', 'system:role:query', 3, 1, 4, '', '#', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '', '2024-05-30 15:03:39');
INSERT INTO `t_menu` VALUES (15, '角色新增', 'system:role:save', 3, 2, 4, '', '', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-06-11 15:12:19');
INSERT INTO `t_menu` VALUES (16, '角色修改', 'system:role:update', 3, 3, 4, '', '', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:03:44');
INSERT INTO `t_menu` VALUES (17, '角色删除', 'system:role:delete', 3, 4, 4, '', '', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:03:46');
INSERT INTO `t_menu` VALUES (18, '角色导出', 'system:role:export', 3, 5, 4, '', '#', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '', '2024-05-30 15:03:58');
INSERT INTO `t_menu` VALUES (19, '菜单查询', 'system:menu:query', 3, 1, 5, '', '#', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '', '2024-05-30 15:04:02');
INSERT INTO `t_menu` VALUES (20, '菜单新增', 'system:menu:save', 3, 2, 5, '', '#', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '', '2024-06-11 15:12:13');
INSERT INTO `t_menu` VALUES (21, '菜单修改', 'system:menu:update', 3, 3, 5, '', '#', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '', '2024-05-30 15:04:07');
INSERT INTO `t_menu` VALUES (22, '菜单删除', 'system:menu:delete', 3, 4, 5, '', '#', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '', '2024-05-30 15:04:09');
INSERT INTO `t_menu` VALUES (23, '设置角色菜单权限', 'system:permission:assign-role-menu', 3, 6, 4, '', '', '', NULL, 1, 1, 1, 1, '', '2021-01-06 17:53:44', '', '2024-05-30 15:04:11');
INSERT INTO `t_menu` VALUES (24, '字典查询', 'system:dict:query', 3, 1, 6, '#', '#', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '', '2024-05-30 15:04:15');
INSERT INTO `t_menu` VALUES (25, '字典新增', 'system:dict:save', 3, 2, 6, '', '', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:04:18');
INSERT INTO `t_menu` VALUES (26, '字典修改', 'system:dict:update', 3, 3, 6, '', '', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:04:20');
INSERT INTO `t_menu` VALUES (27, '字典删除', 'system:dict:delete', 3, 4, 6, '', '', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '1', '2024-05-30 15:04:23');
INSERT INTO `t_menu` VALUES (28, '字典导出', 'system:dict:export', 3, 5, 6, '#', '#', '', NULL, 1, 1, 1, 1, 'admin', '2021-01-05 17:03:48', '', '2024-05-30 15:04:25');
INSERT INTO `t_menu` VALUES (29, '日志监控', '', 1, 11, 0, '/log', 'ep:document', '', '', 1, 1, 1, 1, '', '2024-05-17 10:43:23', '', '2024-05-30 15:04:30');
INSERT INTO `t_menu` VALUES (30, '操作日志', '', 2, 1, 29, 'operate-log', 'fa-solid:allergies', 'system/operatelog/index', 'SystemOperateLog', 1, 1, 1, 1, '', '2024-05-17 10:47:08', '', '2024-05-30 15:04:41');
INSERT INTO `t_menu` VALUES (31, '异常日志', '', 2, 2, 29, 'error-log', 'fa-solid:holly-berry', 'system/errorlog/index', 'SystemErrorLog', 1, 1, 1, 1, '', '2024-05-17 10:49:28', '', '2024-05-30 15:04:45');
INSERT INTO `t_menu` VALUES (32, '调度任务', '', 1, 1, 2, 'job', 'fa:calendar', '', '', 1, 1, 1, 1, '', '2024-05-17 18:27:30', '', '2024-05-30 15:04:48');
INSERT INTO `t_menu` VALUES (33, '定时任务', '', 2, 1, 32, 'job-task', 'fa:calendar-check-o', 'server/jobtask/index', 'MonitorJobTask', 1, 1, 1, 1, '', '2024-05-17 18:06:14', '', '2024-05-30 15:05:01');
INSERT INTO `t_menu` VALUES (34, '调度日志', '', 2, 2, 32, 'job-log', 'fa-solid:abacus', 'server/joblog/index', 'MonitorJobLog', 1, 1, 1, 1, '', '2024-05-17 18:21:42', '', '2024-05-30 15:05:01');
INSERT INTO `t_menu` VALUES (35, '任务新增', 'system:jobtask:save', 3, 1, 33, '', '', '', '', 1, 1, 1, 1, '', '2024-05-20 15:52:34', '', '2024-05-30 15:05:39');
INSERT INTO `t_menu` VALUES (36, '任务修改', 'system:jobtask:update', 3, 2, 33, '', '', '', '', 1, 1, 1, 1, '', '2024-05-20 15:52:56', '', '2024-05-30 15:05:41');
INSERT INTO `t_menu` VALUES (37, '任务查询', 'system:jobtask:query', 3, 3, 33, '', '', '', '', 1, 1, 1, 1, '', '2024-05-20 15:53:45', '', '2024-05-30 15:05:43');
INSERT INTO `t_menu` VALUES (38, '任务删除', 'system:jobtask:delete', 3, 4, 33, '', '', '', '', 1, 1, 1, 1, '', '2024-05-20 15:54:02', '', '2024-05-30 15:05:45');
INSERT INTO `t_menu` VALUES (39, '执行一次', 'system:jobtask:run', 3, 5, 33, '', '', '', '', 1, 1, 1, 1, '', '2024-05-20 15:54:17', '', '2024-05-30 15:05:50');
INSERT INTO `t_menu` VALUES (40, '存储管理', '', 2, 3, 2, 'file', 'ep:upload-filled', 'server/file/index', 'ServerFileConfig', 1, 1, 1, 1, '', '2024-05-23 13:50:46', '', '2024-05-30 15:05:57');
INSERT INTO `t_menu` VALUES (41, '文件查询', 'server:file:query', 3, 1, 40, '', '', '', '', 1, 1, 1, 1, '', '2024-05-23 17:11:13', '', '2024-05-30 15:06:20');
INSERT INTO `t_menu` VALUES (42, '文件删除', 'server:file:delete', 3, 2, 40, '', '', '', '', 1, 1, 1, 1, '', '2024-05-23 17:11:34', '', '2024-05-30 15:06:20');
INSERT INTO `t_menu` VALUES (43, '文件上传', 'server:file:upload', 3, 3, 40, '', '', '', '', 1, 1, 1, 1, '', '2024-05-23 17:11:54', '', '2024-05-30 15:06:20');
INSERT INTO `t_menu` VALUES (44, '配置查询', 'system:config:query', 3, 1, 7, '', '', '', '', 1, 1, 1, 1, '', '2024-05-25 13:29:51', '', '2024-06-11 15:39:36');
INSERT INTO `t_menu` VALUES (45, '配置新增', 'system:config:save', 3, 2, 7, '', '', '', '', 1, 1, 1, 1, '', '2024-05-25 13:30:13', '', '2024-05-30 15:06:23');
INSERT INTO `t_menu` VALUES (46, '配置修改', 'system:config:update', 3, 3, 7, '', '', '', '', 1, 1, 1, 1, '', '2024-05-25 13:30:33', '', '2024-05-30 15:06:25');
INSERT INTO `t_menu` VALUES (47, '配置删除', 'system:config:delete', 3, 4, 7, '', '', '', '', 1, 1, 1, 1, '', '2024-05-25 13:30:52', '', '2024-05-30 15:06:28');
INSERT INTO `t_menu` VALUES (48, '配置导出', 'system:config:export', 3, 5, 7, '', '', '', '', 1, 1, 1, 1, '', '2024-05-25 13:31:16', '', '2024-05-30 15:06:31');
INSERT INTO `t_menu` VALUES (49, '系统监控', 'system:monitor:systemInfo', 2, 4, 2, 'system-monitor', 'fa:area-chart', 'server/system/index', 'MonitorSystemInfo', 1, 1, 1, 1, '', '2024-05-30 11:45:23', '', '2024-06-11 15:20:29');
INSERT INTO `t_menu` VALUES (50, 'redis监控', 'system:monitor:cacheInfo', 2, 5, 2, 'redis-monitor', 'fa-solid:abacus', 'server/redis/index', 'MonitorRedisInfo', 1, 1, 1, 1, '', '2024-05-30 13:25:31', '', '2024-06-11 15:20:55');
INSERT INTO `t_menu` VALUES (52, 'mysql监控', '', 2, 5, 2, 'mysql-monitor', 'ep:coin', 'server/mysql/index', 'MonitorMysqlInfo', 1, 1, 1, 1, '', '2024-05-30 16:12:17', '', '2024-05-30 16:12:17');
INSERT INTO `t_menu` VALUES (53, '应用监控', 'system:monitor:serviceInfo', 2, 6, 2, 'service-monitor', 'fa:server', 'server/service/index', 'MonitorServiceInfo', 1, 1, 1, 1, '', '2024-05-30 16:13:42', '', '2024-07-01 14:25:47');
INSERT INTO `t_menu` VALUES (54, '消息中心', '', 1, 6, 0, '/message', 'ep:bell-filled', '', '', 1, 1, 1, 1, '', '2024-05-30 17:10:25', '', '2024-06-13 11:26:25');
INSERT INTO `t_menu` VALUES (55, '评论管理', '', 2, 1, 54, 'comment', 'ep:chat-dot-square', '/message/comment/index', 'MessageComment', 1, 1, 1, 1, '', '2024-05-30 17:14:13', '', '2024-05-30 17:14:13');
INSERT INTO `t_menu` VALUES (56, '留言管理', '', 2, 2, 54, 'leave', 'ep:message', '/message/leave/index', 'MessageLeave', 1, 1, 1, 1, '', '2024-05-30 17:16:46', '', '2024-05-30 17:29:03');
INSERT INTO `t_menu` VALUES (57, '反馈管理', '', 2, 3, 54, 'feedback', 'ep:checked', '/message/feedback/index', 'MessageFeedback', 1, 1, 1, 1, '', '2024-05-30 17:18:47', '', '2024-05-30 17:18:47');
INSERT INTO `t_menu` VALUES (58, '评论修改', 'system:comment:update', 3, 1, 55, '', '', '', '', 1, 1, 1, 1, '', '2024-05-30 18:15:20', '', '2024-05-30 18:15:20');
INSERT INTO `t_menu` VALUES (59, '评论删除', 'system:comment:delete', 3, 2, 55, '', '', '', '', 1, 1, 1, 1, '', '2024-05-30 18:15:38', '', '2024-05-30 18:15:38');
INSERT INTO `t_menu` VALUES (60, '反馈修改', 'system:feedback:update', 3, 1, 57, '', '', '', '', 1, 1, 1, 1, '', '2024-05-30 18:16:21', '', '2024-05-30 18:16:21');
INSERT INTO `t_menu` VALUES (61, '反馈删除', 'system:feedback:delete', 3, 2, 57, '', '', '', '', 1, 1, 1, 1, '', '2024-05-30 18:16:35', '', '2024-05-30 18:16:35');
INSERT INTO `t_menu` VALUES (62, '留言修改', 'system:leave:update', 3, 1, 56, '', '', '', '', 1, 1, 1, 1, '', '2024-05-30 18:17:36', '', '2024-05-30 18:17:36');
INSERT INTO `t_menu` VALUES (63, '留言删除', 'system:leave:delete', 3, 2, 56, '', '', '', '', 1, 1, 1, 1, '', '2024-05-30 18:17:53', '', '2024-05-30 18:17:53');
INSERT INTO `t_menu` VALUES (64, '网站配置', '', 1, 7, 0, '/website', 'fa-solid:atom', '', '', 1, 1, 1, 1, '', '2024-06-03 09:50:53', '', '2024-06-13 11:26:38');
INSERT INTO `t_menu` VALUES (65, '友情链接', '', 2, 1, 64, 'friendlink', 'fa-solid:link', 'website/friendlink/index', 'WebFriendLink', 1, 1, 1, 1, '', '2024-06-03 09:53:01', '', '2024-06-03 09:53:01');
INSERT INTO `t_menu` VALUES (66, '友链新增', 'system:friendlink:save', 3, 1, 65, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 10:10:06', '', '2024-06-03 10:10:06');
INSERT INTO `t_menu` VALUES (67, '友链修改', 'system:friendlink:update', 3, 2, 65, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 10:10:27', '', '2024-06-03 10:10:27');
INSERT INTO `t_menu` VALUES (68, '友链查询', 'system:friendlink:query', 3, 3, 65, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 10:10:49', '', '2024-06-03 10:10:49');
INSERT INTO `t_menu` VALUES (69, '友链删除', 'system:friendlink:delete', 3, 4, 65, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 10:11:04', '', '2024-06-03 10:11:04');
INSERT INTO `t_menu` VALUES (70, '创作中心', '', 1, 5, 0, '/articles', 'ep:document-copy', '', '', 1, 1, 1, 1, '', '2024-06-03 11:01:30', '', '2024-06-03 11:01:30');
INSERT INTO `t_menu` VALUES (71, '分类管理', '', 2, 2, 70, 'article-category', 'ep:document-checked', 'articles/category/index', 'ArticlesCategory', 1, 1, 1, 1, '', '2024-06-03 11:04:54', '', '2024-06-03 11:08:01');
INSERT INTO `t_menu` VALUES (72, '标签管理', '', 2, 3, 70, 'article-tag', 'ep:collection-tag', 'articles/tag/index', 'ArticleTag', 1, 1, 1, 1, '', '2024-06-03 11:05:51', '', '2024-06-03 13:23:04');
INSERT INTO `t_menu` VALUES (73, '文章管理', '', 2, 1, 70, 'article', 'ep:document', 'articles/article/index', 'Article', 1, 1, 1, 1, '', '2024-06-03 11:07:21', '', '2024-06-03 11:07:56');
INSERT INTO `t_menu` VALUES (74, '分类新增', 'system:articlecategory:save', 3, 1, 71, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:11:09', '', '2024-06-03 11:11:09');
INSERT INTO `t_menu` VALUES (75, '分类修改', 'system:articlecategory:update', 3, 2, 71, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:11:27', '', '2024-06-03 11:11:27');
INSERT INTO `t_menu` VALUES (76, '分类查询', 'system:articlecategory:query', 3, 3, 71, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:11:43', '', '2024-06-03 11:11:43');
INSERT INTO `t_menu` VALUES (77, '分类删除', 'system:articlecategory:delete', 3, 4, 71, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:11:59', '', '2024-06-03 11:11:59');
INSERT INTO `t_menu` VALUES (78, '标签新增', 'system:articletag:save', 3, 1, 72, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:12:22', '', '2024-06-03 11:12:22');
INSERT INTO `t_menu` VALUES (79, '标签修改', 'system:articletag:update', 3, 2, 72, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:12:38', '', '2024-06-03 11:12:38');
INSERT INTO `t_menu` VALUES (80, '标签查询', 'system:articletag:query', 3, 3, 72, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:12:53', '', '2024-06-03 11:12:53');
INSERT INTO `t_menu` VALUES (81, '标签删除', 'system:articletag:delete', 3, 4, 72, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:13:13', '', '2024-06-03 11:13:13');
INSERT INTO `t_menu` VALUES (82, '文章新增', 'system:article:save', 3, 1, 73, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:13:34', '', '2024-06-03 11:13:34');
INSERT INTO `t_menu` VALUES (83, '文字修改', 'system:article:update', 3, 2, 73, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:13:50', '', '2024-06-03 11:13:50');
INSERT INTO `t_menu` VALUES (84, '文章查询', 'system:article:query', 3, 3, 73, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:14:12', '', '2024-06-03 11:14:12');
INSERT INTO `t_menu` VALUES (85, '文章删除', 'system:article:delete', 3, 4, 73, '', '', '', '', 1, 1, 1, 1, '', '2024-06-03 11:14:26', '', '2024-06-03 11:14:26');
INSERT INTO `t_menu` VALUES (86, '文章导出', 'system:article:export', 3, 5, 73, '', '', '', '', 1, 1, 1, 1, '', '2024-06-04 16:47:10', '', '2024-06-04 16:47:10');
INSERT INTO `t_menu` VALUES (87, '调度日志查询', 'system:joblog:query', 3, 1, 34, '', '', '', '', 1, 1, 1, 1, '', '2024-06-11 15:36:35', '', '2024-06-11 15:36:35');
INSERT INTO `t_menu` VALUES (88, '调度日志删除', 'system:joblog:delete', 3, 2, 34, '', '', '', '', 1, 1, 1, 1, '', '2024-06-11 15:36:58', '', '2024-06-11 15:36:58');
INSERT INTO `t_menu` VALUES (89, '服务网关', '', 1, 9, 0, '/gateway', 'fa:google-wallet', '', '', 1, 1, 1, 1, '', '2024-06-13 11:26:11', '', '2024-06-13 11:26:11');
INSERT INTO `t_menu` VALUES (90, '网关配置', '', 2, 1, 89, 'gateway-config', 'ep:setting', 'gateway/config/index', 'GatewayConfig', 1, 1, 1, 1, '', '2024-06-13 11:28:10', '', '2024-06-13 11:28:10');
INSERT INTO `t_menu` VALUES (91, '网关日志', '', 2, 2, 89, 'gateway-log', 'fa:area-chart', 'gateway/log/index', 'GatewayLog', 1, 1, 1, 1, '', '2024-06-13 11:29:44', '', '2024-06-13 11:29:44');
INSERT INTO `t_menu` VALUES (92, '网关配置新增', 'gateway:config:save', 3, 1, 90, '', '', '', '', 1, 1, 1, 1, '', '2024-06-13 11:45:33', '', '2024-06-13 11:46:57');
INSERT INTO `t_menu` VALUES (93, '网关配置修改', 'gateway:config:update', 3, 2, 90, '', '', '', '', 1, 1, 1, 1, '', '2024-06-13 11:45:58', '', '2024-06-13 11:45:58');
INSERT INTO `t_menu` VALUES (94, '网关配置删除', 'gateway:config:delete', 3, 3, 90, '', '', '', '', 1, 1, 1, 1, '', '2024-06-13 11:46:22', '', '2024-06-13 11:46:22');
INSERT INTO `t_menu` VALUES (95, '网关配置查询', 'gateway:config:query', 3, 4, 90, '', '', '', '', 1, 1, 1, 1, '', '2024-06-13 11:46:44', '', '2024-06-13 11:46:44');
INSERT INTO `t_menu` VALUES (96, '网关日志查询', 'gateway:log:query', 3, 1, 91, '', '', '', '', 1, 1, 1, 1, '', '2024-06-13 11:47:35', '', '2024-06-13 11:47:35');
INSERT INTO `t_menu` VALUES (97, '网站信息', 'system:siteconfig:info', 2, 2, 64, 'siteconfig', 'ep:monitor', 'website/siteconfig/index', 'WebSiteConfig', 1, 1, 1, 1, '', '2024-06-27 14:48:30', '', '2024-06-27 14:48:30');
INSERT INTO `t_menu` VALUES (98, '配置保存', 'system:siteconfig:save', 3, 1, 97, '', '', '', '', 1, 1, 1, 1, '', '2024-06-27 14:49:06', '', '2024-06-27 14:49:06');
INSERT INTO `t_menu` VALUES (99, '说说管理', '', 2, 4, 54, 'talk', 'ep:chat-dot-round', '/message/talk/index', 'MessageTalk', 1, 1, 1, 1, '', '2024-08-02 17:11:06', '', '2024-08-02 17:11:06');
INSERT INTO `t_menu` VALUES (100, '说说新增', 'system:talk:save', 3, 1, 99, '', '', '', '', 1, 1, 1, 1, '', '2024-08-02 17:13:16', '', '2024-08-02 17:13:16');
INSERT INTO `t_menu` VALUES (101, '说说修改', 'system:talk:update', 3, 1, 99, '', '', '', '', 1, 1, 1, 1, '', '2024-08-02 17:13:46', '', '2024-08-02 17:13:46');
INSERT INTO `t_menu` VALUES (102, '说说删除', 'system:talk:delete', 3, 3, 99, '', '', '', '', 1, 1, 1, 1, '', '2024-08-02 17:14:10', '', '2024-08-02 18:05:11');
INSERT INTO `t_menu` VALUES (103, '说说查询', 'system:talk:query', 3, 4, 99, '', '', '', '', 1, 1, 1, 1, '', '2024-08-02 17:14:24', '', '2024-08-02 17:14:24');
INSERT INTO `t_menu` VALUES (104, '轮播管理', '', 2, 3, 64, 'carousel', 'fa-solid:audio-description', 'website/carousel/index', 'WebSiteCarousel', 1, 1, 1, 1, '', '2024-08-05 13:13:43', '', '2024-08-05 13:13:43');
INSERT INTO `t_menu` VALUES (105, '轮播新增', 'system:carousel:save', 3, 1, 104, '', '', '', '', 1, 1, 1, 1, '', '2024-08-05 13:14:27', '', '2024-08-05 13:14:27');
INSERT INTO `t_menu` VALUES (106, '轮播修改', 'system:carousel:update', 3, 2, 104, '', '', '', '', 1, 1, 1, 1, '', '2024-08-05 13:14:45', '', '2024-08-05 13:14:45');
INSERT INTO `t_menu` VALUES (107, '轮播删除', 'system:carousel:delete', 3, 3, 104, '', '', '', '', 1, 1, 1, 1, '', '2024-08-05 13:15:05', '', '2024-08-05 13:15:05');
INSERT INTO `t_menu` VALUES (108, '轮播查询', 'system:carousel:query', 3, 4, 104, '', '', '', '', 1, 1, 1, 1, '', '2024-08-05 13:15:21', '', '2024-08-05 13:15:21');
INSERT INTO `t_menu` VALUES (109, '文章导入', 'system:article:import', 3, 6, 73, '', '', '', '', 1, 1, 1, 1, '', '2025-01-22 14:55:52', '', '2025-01-22 14:55:52');

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `to_user_id` bigint(20) NOT NULL COMMENT '发送用户id',
  `to_user_nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送用户昵称',
  `to_user_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送用户头像',
  `from_user_id` bigint(20) NOT NULL COMMENT '接收用户id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知内容',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `region` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `latitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `longitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经度',
  `status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '是否已读:0未读,1已读',
  `notice_type` tinyint(3) NOT NULL COMMENT '通知类型:1系统通知2评论,3点赞',
  `type_id` bigint(20) NULL DEFAULT NULL COMMENT '通知类型id:系统通知null',
  `notice_type_path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'notice' COMMENT '通知类型路径:系统通知notice,talk/article/friend',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_type_user`(`notice_type`, `from_user_id`, `status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统通知' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES (3, 4, '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 1, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 0, 2, 3, 'talk', '2024-04-23 18:35:36', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (4, 4, '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 4, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 1, 3, 3, 'talk', '2024-04-23 18:35:36', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (5, 4, '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 1, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 0, 2, 1, 'talk', '2024-04-23 18:35:36', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (6, 4, '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 1, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 0, 2, 3, 'talk', '2024-04-23 18:35:36', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (7, 4, '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 4, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 1, 3, 3, 'talk', '2024-04-23 18:35:36', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (8, 4, '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 4, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 1, 3, 43, 'friend', '2024-04-23 18:35:36', '2024-10-29 17:46:21');
INSERT INTO `t_notice` VALUES (9, 4, '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 4, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 1, 3, 43, 'friend', '2024-04-23 18:35:36', '2024-10-29 17:46:21');
INSERT INTO `t_notice` VALUES (10, 4, '鸢尾', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 4, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 1, 3, 43, 'friend', '2024-04-23 18:35:36', '2024-10-29 17:46:21');
INSERT INTO `t_notice` VALUES (11, 17, '用户309415', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 1, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 2, 8, 'article', '2024-09-30 09:23:39', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (12, 17, '用户309415', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 1, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 2, 8, 'article', '2024-09-30 09:23:39', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (13, 16, '用户602459', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 17, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 1, 3, 64, 'article', '2024-09-30 09:22:24', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (14, 16, '用户602459', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 64, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 2, 8, 'article', '2024-09-30 09:22:24', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (15, 16, '用户602459', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 16, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 1, 3, 65, 'article', '2024-09-30 09:22:24', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (16, 16, '用户602459', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 17, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 1, 3, 63, 'article', '2024-09-30 09:22:24', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (17, 16, '用户602459', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 63, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 2, 8, 'article', '2024-09-30 09:22:24', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (18, 16, '用户602459', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 63, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 2, 8, 'article', '2024-09-30 09:22:24', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (19, 17, '这是被评论用户', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 1, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 2, 5, 'article', '2024-09-30 09:23:39', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (21, 16, '这是评论用户', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 17, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 1, 3, 68, 'article', '2024-09-30 09:22:24', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (24, 17, '这是被评论用户', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 4, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 3, 62, 'talk', '2024-09-30 09:23:39', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (25, 17, '这是被评论用户', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 4, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 3, 62, 'talk', '2024-09-30 09:23:39', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (26, 17, '这是被评论用户', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 4, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 2, 3, 'talk', '2024-09-30 09:23:39', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (27, 17, '这是被评论用户', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 1, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 2, 3, 'talk', '2024-09-30 09:23:39', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (28, 17, '这是被评论用户', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 1, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 2, 3, 'talk', '2024-09-30 09:23:39', '2024-10-29 17:46:35');
INSERT INTO `t_notice` VALUES (29, 17, '这是被评论用户', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 1, '您评论的友链有了新动态,请点击前往查看!', '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 0, 2, 3, 'talk', '2024-09-30 09:23:39', '2024-10-29 17:46:35');

-- ----------------------------
-- Table structure for t_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `t_operate_log`;
CREATE TABLE `t_operate_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '操作用户id',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作用户名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求的接口',
  `request_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `request_class_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求类名',
  `request_method` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `request_params` json NULL COMMENT '请求参数',
  `request_time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求耗时',
  `request_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求ip',
  `request_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `operate_desc` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作描述',
  `operate_os` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `error_info` json NULL COMMENT '异常信息json格式',
  `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常信息的message',
  `log_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '日志类型:0操作日志,1异常日志',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 208 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_operate_log
-- ----------------------------
INSERT INTO `t_operate_log` VALUES (168, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"47I7lDadAalNEONB9l67Rb34JNxf5Odpi9rMS2gEJO1IqRVHNkIT/uCuh1SfT11vzG7DZ7HhqJ78IOdIXSTj5g==\"}', '1497', '192.168.56.1', '全球', '用户登录', 'Chrome 126-Windows 10', NULL, NULL, 0, '2024-07-16 10:09:38', '2024-07-16 10:09:38');
INSERT INTO `t_operate_log` VALUES (169, 1, 'admin', '/iris/system/job/run', 'GET', 'com.iris.blog.controller.system.ScheduleJobController', 'run', '[22]', '35', '192.168.56.1', '全球', '立即执行定时任务', 'Chrome 126-Windows 10', NULL, NULL, 0, '2024-07-16 10:30:00', '2024-07-16 10:30:00');
INSERT INTO `t_operate_log` VALUES (170, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"kj3NnrHsALDwKsRKnck0J3ja/xXJZbw/eqnOWE1zL9oMDnTA8Rg322XGrjbRxTUW5KlqfpNtuzsjzN0fDwXQLg==\"}', '1521', '192.168.56.1', '全球', '用户登录', 'Chrome 126-Windows 10', NULL, NULL, 0, '2024-07-24 13:35:41', '2024-07-24 13:35:41');
INSERT INTO `t_operate_log` VALUES (171, 1, 'admin', '/iris/system/gateway/config/update', 'POST', 'com.iris.blog.controller.system.GatewayConfigController', 'updateGatewayConfig', '[{\"id\": 1, \"name\": \"Nginx日志配置\", \"path\": \"C:\\\\Users\\\\admin\\\\Desktop\\\\fsdownload\\\\access.log\", \"fileName\": \"access.log\", \"position\": 0}]', '41', '192.168.56.1', '全球', '修改网关配置', 'Chrome 126-Windows 10', NULL, NULL, 0, '2024-07-24 14:00:00', '2024-07-24 14:00:00');
INSERT INTO `t_operate_log` VALUES (172, 1, 'admin', '/iris/system/job/run', 'GET', 'com.iris.blog.controller.system.ScheduleJobController', 'run', '[22]', '11', '192.168.56.1', '全球', '立即执行定时任务', 'Chrome 126-Windows 10', NULL, NULL, 0, '2024-07-24 14:00:00', '2024-07-24 14:00:00');
INSERT INTO `t_operate_log` VALUES (173, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"U1eipY4d/UYLA0pT0QIpcq/VVxrJ4SWpauGcHH/a5rh3bwlT5WYT4eQ/WTu3Xc4jQgK5zbTUi/YgdCJK4f4B8A==\"}', '1082', '192.168.56.1', '全球', '用户登录', 'Chrome 126-Windows 10', NULL, NULL, 0, '2024-07-24 15:05:12', '2024-07-24 15:05:12');
INSERT INTO `t_operate_log` VALUES (174, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"PgCf0LiT3jYgO5aHTE6BJOuDRQgKdFOkvOvN4T8KHrJ82oa4yIvY2KOSaph5M+gj0fKZwVyPglYwPL+qt9u+cQ==\"}', '1409', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 16:32:04', '2024-08-02 16:32:04');
INSERT INTO `t_operate_log` VALUES (175, 1, 'admin', '/iris/system/menu/save', 'POST', 'com.iris.blog.controller.system.MenuController', 'saveMenu', '[{\"id\": 0, \"icon\": \"ep:chat-dot-round\", \"name\": \"说说管理\", \"path\": \"talk\", \"sort\": 4, \"type\": 2, \"status\": 1, \"visible\": true, \"parentId\": 54, \"component\": \"/message/talk/index\", \"keepAlive\": true, \"alwaysShow\": true, \"permission\": \"\", \"componentName\": \"MessageTalk\"}]', '86', '192.168.56.1', '全球', '保存菜单权限', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 17:30:00', '2024-08-02 17:30:00');
INSERT INTO `t_operate_log` VALUES (176, 1, 'admin', '/iris/system/menu/save', 'POST', 'com.iris.blog.controller.system.MenuController', 'saveMenu', '[{\"id\": 0, \"icon\": \"\", \"name\": \"说说新增\", \"path\": \"\", \"sort\": 1, \"type\": 3, \"status\": 1, \"visible\": true, \"parentId\": 99, \"component\": \"\", \"keepAlive\": true, \"alwaysShow\": true, \"permission\": \"system:talk:save\", \"componentName\": \"\"}]', '94', '192.168.56.1', '全球', '保存菜单权限', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 17:30:00', '2024-08-02 17:30:00');
INSERT INTO `t_operate_log` VALUES (177, 1, 'admin', '/iris/system/menu/save', 'POST', 'com.iris.blog.controller.system.MenuController', 'saveMenu', '[{\"id\": 0, \"icon\": \"\", \"name\": \"说说修改\", \"path\": \"\", \"sort\": 1, \"type\": 3, \"status\": 1, \"visible\": true, \"parentId\": 99, \"component\": \"\", \"keepAlive\": true, \"alwaysShow\": true, \"permission\": \"system:talk:update\", \"componentName\": \"\"}]', '71', '192.168.56.1', '全球', '保存菜单权限', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 17:30:00', '2024-08-02 17:30:00');
INSERT INTO `t_operate_log` VALUES (178, 1, 'admin', '/iris/system/menu/save', 'POST', 'com.iris.blog.controller.system.MenuController', 'saveMenu', '[{\"id\": 0, \"icon\": \"\", \"name\": \"说说删除\", \"path\": \"\", \"sort\": 3, \"type\": 3, \"status\": 1, \"visible\": true, \"parentId\": 99, \"component\": \"\", \"keepAlive\": true, \"alwaysShow\": true, \"permission\": \"system:talk:update\", \"componentName\": \"\"}]', '102', '192.168.56.1', '全球', '保存菜单权限', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 17:30:00', '2024-08-02 17:30:00');
INSERT INTO `t_operate_log` VALUES (179, 1, 'admin', '/iris/system/menu/save', 'POST', 'com.iris.blog.controller.system.MenuController', 'saveMenu', '[{\"id\": 0, \"icon\": \"\", \"name\": \"说说查询\", \"path\": \"\", \"sort\": 4, \"type\": 3, \"status\": 1, \"visible\": true, \"parentId\": 99, \"component\": \"\", \"keepAlive\": true, \"alwaysShow\": true, \"permission\": \"system:talk:query\", \"componentName\": \"\"}]', '24', '192.168.56.1', '全球', '保存菜单权限', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 17:30:01', '2024-08-02 17:30:01');
INSERT INTO `t_operate_log` VALUES (180, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"TfEMUhTK/uA8TNhvSheN8jzBUKCZwIWTxpLVbaLyvZz1ZFnzxUUEuZzB+gpO5dcrsgVk+f6/s0jLxWZL2+xcFA==\"}', '942', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 17:45:44', '2024-08-02 17:45:44');
INSERT INTO `t_operate_log` VALUES (181, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"h2iaf5d5/DpDZdngqzsS5jtJPLX2ugqGy+Bvhz43qd5/M7LRM4ddnP8Bl06kGPiFvxkv2loP/VlirpHZLuFtSA==\"}', '973', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 17:47:54', '2024-08-02 17:47:54');
INSERT INTO `t_operate_log` VALUES (182, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"3SD8A5LVKo9HYnU6m8G0mo5v5pTnIQQjJb2q4JNxushoJ+IaY7Ef3B9Zm6K8/G5hnW1uBHT7Ywbr7qLnevbbMg==\"}', '1212', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 17:48:57', '2024-08-02 17:48:57');
INSERT INTO `t_operate_log` VALUES (183, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"We6R953RPAWRHHj/1h4XK3Q4gL3msjYlMXaciLy08dUe6PcYHFVhbcy+WtIf8E+YPHYZG6/bKLXDuwLHAMpaSQ==\"}', '1550', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 17:57:39', '2024-08-02 17:57:39');
INSERT INTO `t_operate_log` VALUES (184, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"EWGBZHIAz5n8NRsI+N+l5XfchO+2Z4tfXnubciokRPJXvNgFrxojoEfEIHuxCQJce3sQpOGt7Byd1KZrqQAGwg==\"}', '1369', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 18:00:04', '2024-08-02 18:00:04');
INSERT INTO `t_operate_log` VALUES (185, 1, 'admin', '/iris/system/config/update/code/value', 'GET', 'com.iris.blog.controller.system.SysConfigController', 'updateValueByCode', '[\"SYS_OSS_CONFIG_KEY\", \"{\\\"aliyunAccessKeyId\\\":\\\"LTAI5tLJKshMDHiGVxeatCaR\\\",\\\"aliyunAccessKeySecret\\\":\\\"IC5623RxiFT1tqOrbndYwvKTnDK3vL\\\",\\\"aliyunBucketName\\\":\\\"zuiwanjia-images\\\",\\\"aliyunDomain\\\":\\\"https://pzdsoss.pzds.com\\\",\\\"aliyunEndPoint\\\":\\\"https://oss-cn-chengdu.aliyuncs.com\\\",\\\"localDomain\\\":\\\"D:/uploadFile/\\\",\\\"localPath\\\":\\\"D:/uploadFile/\\\",\\\"qcloudAppId\\\":1300679084,\\\"qcloudBucketName\\\":\\\"echo20\\\",\\\"qcloudDomain\\\":\\\"https://echo20-1300679084.cos.ap-chengdu.myqcloud.com\\\",\\\"qcloudRegion\\\":\\\"ap-chengdu\\\",\\\"qcloudSecretId\\\":\\\"AKIDwkXCdpdKp57wWs0tYyAKFuKUTHuQlvqZ\\\",\\\"qcloudSecretKey\\\":\\\"hDWbdvvqgKmz4zg9VhgPxJQIyJRTM2Xp\\\",\\\"qiniuAccessKey\\\":\\\"3TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS\\\",\\\"qiniuBucketName\\\":\\\"ruoyi-vue-pro\\\",\\\"qiniuDomain\\\":\\\"http://test.yudao.iocoder.cn\\\",\\\"qiniuSecretKey\\\":\\\"wd0tbVBYlp0S-ihA8Qg2hPLncoP83wyrIq24OZuY\\\",\\\"type\\\":1,\\\"directoryList\\\":\\\"goods\\\",\\\"minioEndPoint\\\":\\\"http://192.168.56.10:9090\\\",\\\"minioAccessKey\\\":\\\"1ONPOssH5F2HGzRCiitp\\\",\\\"minioSecretKey\\\":\\\"ewhKA8Axw5RJWGECoCPAZI2NVIoFu6cViNhhcKcH\\\",\\\"minioBucketName\\\":\\\"test-bucket\\\"}\"]', '176', '192.168.56.1', '全球', '根据参数编码，更新参数值', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-02 18:30:00', '2024-08-02 18:30:00');
INSERT INTO `t_operate_log` VALUES (186, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"wuEtzOXGAhQ+RPL8U+BaymwpSghi9qvm45LTB9b4K11SLaKaLEdyp46+0SGk7jl6INmSlMbyq5J1nloUoA7Sbg==\"}', '1449', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-05 13:10:52', '2024-08-05 13:10:52');
INSERT INTO `t_operate_log` VALUES (187, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"HsEjnhSjcATzi/gIih/Ck10B/qc6toyVtwYjw8Px7ozbdMoR/4dvjnyhUOYqQze/DX9aSV30eRgaQRDbAXAu5g==\"}', '1214', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-05 13:23:50', '2024-08-05 13:23:50');
INSERT INTO `t_operate_log` VALUES (188, 1, 'admin', '/iris/system/config/update/code/value', 'GET', 'com.iris.blog.controller.system.SysConfigController', 'updateValueByCode', '[\"SYS_OSS_CONFIG_KEY\", \"{\\\"aliyunAccessKeyId\\\":\\\"LTAI5tLJKshMDHiGVxeatCaR\\\",\\\"aliyunAccessKeySecret\\\":\\\"IC5623RxiFT1tqOrbndYwvKTnDK3vL\\\",\\\"aliyunBucketName\\\":\\\"zuiwanjia-images\\\",\\\"aliyunDomain\\\":\\\"https://pzdsoss.pzds.com\\\",\\\"aliyunEndPoint\\\":\\\"https://oss-cn-chengdu.aliyuncs.com\\\",\\\"localDomain\\\":\\\"D:/uploadFile/\\\",\\\"localPath\\\":\\\"D:/uploadFile/\\\",\\\"qcloudAppId\\\":1300679084,\\\"qcloudBucketName\\\":\\\"echo20\\\",\\\"qcloudDomain\\\":\\\"https://echo20-1300679084.cos.ap-chengdu.myqcloud.com\\\",\\\"qcloudRegion\\\":\\\"ap-chengdu\\\",\\\"qcloudSecretId\\\":\\\"AKIDwkXCdpdKp57wWs0tYyAKFuKUTHuQlvqZ\\\",\\\"qcloudSecretKey\\\":\\\"hDWbdvvqgKmz4zg9VhgPxJQIyJRTM2Xp\\\",\\\"qiniuAccessKey\\\":\\\"3TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS\\\",\\\"qiniuBucketName\\\":\\\"ruoyi-vue-pro\\\",\\\"qiniuDomain\\\":\\\"http://test.yudao.iocoder.cn\\\",\\\"qiniuSecretKey\\\":\\\"wd0tbVBYlp0S-ihA8Qg2hPLncoP83wyrIq24OZuY\\\",\\\"type\\\":1,\\\"directoryList\\\":\\\"goods\\\",\\\"minioEndPoint\\\":\\\"http://192.168.56.10:9090\\\",\\\"minioAccessKey\\\":\\\"1ONPOssH5F2HGzRCiitp\\\",\\\"minioSecretKey\\\":\\\"ewhKA8Axw5RJWGECoCPAZI2NVIoFu6cViNhhcKcH\\\",\\\"minioBucketName\\\":\\\"test-bucket\\\"}\"]', '51', '192.168.56.1', '全球', '根据参数编码，更新参数值', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-05 14:00:00', '2024-08-05 14:00:00');
INSERT INTO `t_operate_log` VALUES (189, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"osFuNKTM3fTm+EaqF7Q6pgEJmtQI/1OL9Etb+nWffV24/vbpsKAO7G5ejxrt9/NipKyzYDPMqWdfoCvRjySrFw==\"}', '1051', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-05 14:26:01', '2024-08-05 14:26:01');
INSERT INTO `t_operate_log` VALUES (190, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"Cv1DMtur9ENJZai+vBcZjiXi6Yh7xWOz5pCmVUnLbHv1B/qCsK5F6QSusamn4zdXaCs+tSSkC11rqvBQniBPfg==\"}', '1384', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-06 18:18:39', '2024-08-06 18:18:39');
INSERT INTO `t_operate_log` VALUES (191, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"MijsN5VdCS2NVJ9U2MEAjjjOV6egfJ+lalZkrx/zmzfLkg8/ItVIxuIHox8IY/A+U2YWgvbFPlUBNDY7TL9z5g==\"}', '1368', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-07 09:50:02', '2024-08-07 09:50:02');
INSERT INTO `t_operate_log` VALUES (192, 1, 'admin', '/iris/system/article/update', 'POST', 'com.iris.blog.controller.system.ArticleController', 'updateArticle', '[{\"id\": 6, \"intro\": \"MarkDown语法\", \"isTop\": false, \"title\": \"MarkDown语法\", \"views\": 123123, \"status\": 2, \"content\": \"# 字符效果\\n\\n- ~~删除线~~ <s>删除线（开启识别 HTML 标签时）</s>\\n\\n- _斜体字_ _斜体字_\\n- **粗体** **粗体**\\n- **_粗斜体_** **_粗斜体_**\\n\\n- 上标：X<sub>2</sub>，下标：O<sup>2</sup>\\n\\n- ==高亮==\\n\\n- `Inline Code`\\n\\n> 引用：如果想要插入空白换行（即 `<br>` 标签），在插入处先键入两个以上的空格然后回车即可\\n\\n# 超链接\\n\\n- [普通链接](https://www.ttkwsd.top)\\n- [_斜体链接_](https://www.ttkwsd.top)\\n- [**粗体链接**](https://www.ttkwsd.top)\\n\\n# 脚注\\n\\n这是一个简单的脚注 [^1] 而这是一个更长的脚注 [^bignote].\\n\\n[^1]: 这是第一个脚注.\\n[^bignote]: 这是一个非常长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长的脚注.\\n\\n# 图像\\n\\n下面是一张我家硝子的壁纸?:\\n![硝子1](https://i.niupic.com/images/2022/03/11/9Wl7.jpg)\\n再来一张好了?：\\n![硝子2](https://i.niupic.com/images/2022/03/12/9Wme.jpg)\\n\\n# 代码\\n\\n## 行内代码\\n\\n在 VS Code 中按下 <kbd>Alt</kbd> + <kbd>T</kbd> + <kbd>R</kbd> 执行命令：`npm install marked`\\n\\n## 代码片\\n\\n### Python 代码\\n\\n```python\\nclass Animal:\\n    \\\"\\\"\\\" 动物类 \\\"\\\"\\\"\\n\\n    def __init__(self, age: int, name: str):\\n        self.age = age\\n        self.name = name\\n\\n    def getInfo(self) -> str:\\n        \\\"\\\"\\\" 返回信息 \\\"\\\"\\\"\\n        return f\'age: {self.age}; name: {self.name}\'\\n\\n\\nclass Dog(Animal):\\n    \\\"\\\"\\\" 狗狗类 \\\"\\\"\\\"\\n\\n    def __init__(self, age: int, name: str, gender=\'female\', color=\'white\'):\\n        super().__init__(age, name)\\n        self.gender = gender\\n        self.__color = color\\n\\n    def bark(self):\\n        \\\"\\\"\\\" 狗叫 \\\"\\\"\\\"\\n        print(\'lololo\')\\n\\n    @property\\n    def color(self):\\n        return self.__color\\n\\n    @color.setter\\n    def color(self, color: str):\\n        if color not in [\'red\', \'white\', \'black\']:\\n            raise ValueError(\'颜色不符合要求\')\\n        self.__color = color\\n\\n\\nif __name__ == \'__main__\':\\n    dog = Dog(16, \'啸天\', gender=\'male\')\\n    # 狗叫\\n    dog.bark()\\n    # 设置狗狗毛色\\n    dog.color = \'blue\'\\n```\\n\\n### HTML 代码\\n\\n```html\\n<!DOCTYPE html>\\n<html>\\n    <head>\\n        <mate charest=\\\"utf-8\\\" />\\n        <title>Hello world!</title>\\n    </head>\\n    <body>\\n        <h1>Hello world!</h1>\\n    </body>\\n</html>\\n```\\n\\n# 列表\\n\\n## 无序列表\\n\\n- 福建\\n  - 厦门\\n  - 福州\\n- 浙江\\n- 江苏\\n\\n## 有序列表\\n\\n1. 动物\\n   1. 人类\\n   2. 犬类\\n2. 植物\\n3. 微生物\\n\\n## 任务列表\\n\\n- [x] 预习计算机网络\\n- [ ] 复习现代控制理论\\n- [ ] 刷现代控制理论历年卷\\n  - [ ] 2019 年期末试卷\\n  - [ ] 2020 年期末试卷\\n\\n# 表格\\n\\n| 项目   |  价格 | 数量 |\\n| ------ | ----: | :--: |\\n| 计算机 | $1600 |  5   |\\n| 手机   |   $12 |  12  |\\n| 管线   |    $1 | 234  |\\n\\n---\\n\\n# 特殊符号\\n\\n&copy; & &uml; &trade; &iexcl; &pound;\\n&amp; &lt; &gt; &yen; &euro; &reg; &plusmn; &para; &sect; &brvbar; &macr; &laquo; &middot;\\n\\nX&sup2; Y&sup3; &frac34; &frac14; &times; &divide; &raquo;\\n\\n18&ordm;C &quot; &apos;\\n\\n# Emoji 表情 🎉\\n\\n- 马：🐎\\n- 星星：✨\\n- 笑脸：😀\\n- 跑步：🏃‍\\n\\n# 数学公式\\n\\n行间公式：\\n$\\\\sin(\\\\alpha)^{\\\\theta}=\\\\sum_{i=0}^{n}(x^i + \\\\cos(f))$\\n行内公式 $E=mc^2$\\n\\n# Tip提示\\n\\n::: tip\\n  在此输入内容\\n:::\\n::: warning\\n  在此输入内容\\n:::\\n::: danger\\n  在此输入内容\\n:::\\n::: details\\n  内容\\n:::\", \"readType\": true, \"isForward\": true, \"coverImage\": \"http://test.yudao.iocoder.cn/goods/20240611/11.jpg\", \"createTime\": \"2024-06-11T15:58:14\", \"forwardUrl\": \"\", \"updateTime\": \"2024-06-14T14:35:44\", \"tagNameList\": [\"Mysql\", \"Linux\", \"Spring Cloud\"], \"categoryName\": \"心情随笔\"}]', '346', '192.168.56.1', '全球', '修改文章', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-08-07 10:00:00', '2024-08-07 10:00:00');
INSERT INTO `t_operate_log` VALUES (193, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"m2BsDs6qD7u1Ox9aAj6VL8NwsnWbfclT8jHjAP1hrvCRs/mW93CaZptPj6z8bzuKh5b+qoX5/sv28fagxx2slA==\"}', '1520', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-09-09 18:35:23', '2024-09-09 18:35:23');
INSERT INTO `t_operate_log` VALUES (194, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"E0P/dIRM2M3BmJz+KLdOz1UbC4BVYjaWvx20E/0Wl2u5tpsd5WqzevaAmr1bPof45qfX9H5c0BgwXmXxETtKrA==\"}', '1362', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-09-13 10:16:03', '2024-09-13 10:16:03');
INSERT INTO `t_operate_log` VALUES (195, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"k4MPCVGxrWNBPy7PaU6iRIOIOyNFjTatYatPV8dyd4QOzUn5J+P8fPqD7Fqu2Uofu4eUdm1Lw59119CGBGeV8g==\"}', '1736', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-09-14 11:12:00', '2024-09-14 11:12:00');
INSERT INTO `t_operate_log` VALUES (196, 1, 'admin', '/iris/system/config/save', 'POST', 'com.iris.blog.controller.system.SysConfigController', 'saveSysConfig', '[{\"remark\": \"邮件服务:emailHost:邮箱地址,emailUsername:邮箱发件人,emailPassword:邮箱授权码,emailPort:邮箱端口\", \"status\": true, \"paramCode\": \"SYS_MAIL_CONFIG_KEY\", \"paramType\": false, \"paramValue\": \"{\\n\\\"emailHost\\\":\\\"smtp.qq.com\\\",\\n\\\"emailUsername\\\":\\\"2416387424@qq.com\\\",\\n\\\"emailPassword\\\":\\\"ptkhvzaqxnpndiid\\\",\\n\\\"emailPort\\\":\\\"587\\\"\\n}\"}]', '108', '192.168.56.1', '全球', '保存系统参数', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-09-14 11:30:00', '2024-09-14 11:30:00');
INSERT INTO `t_operate_log` VALUES (197, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"5SEUh/SIvuB/ndDjyN6cgZfus9pP0Zs1W/vVaW919l2h4hV/lBDC3DFf1k/T8aY8VnIxhSvBIh+NTH9F5+4HPQ==\"}', '899', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-09-14 16:45:27', '2024-09-14 16:45:27');
INSERT INTO `t_operate_log` VALUES (198, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"xhSWT59lXMBMpTgYgSW+GionEhWKWIxpBY1/vQzbz1roNREjTfM+HiKNkFvsp8xwBE6vigpJWYrmDOYr8pB19g==\"}', '984', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-09-19 17:38:04', '2024-09-19 17:38:04');
INSERT INTO `t_operate_log` VALUES (199, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true}', '904', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-09-20 13:40:12', '2024-09-20 13:40:12');
INSERT INTO `t_operate_log` VALUES (200, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"+MKlZ5cHr5r7HKO5EnLx2fqJAM/BfJJzJhpfqQYRMHxlANEOxmWby9ZKqe7VOpgq9jMyAYi9DlC6ipVzmDCdCg==\"}', '941', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-09-20 13:41:43', '2024-09-20 13:41:43');
INSERT INTO `t_operate_log` VALUES (201, 1, 'admin', '/iris/system/config/update/code/value', 'GET', 'com.iris.blog.controller.system.SysConfigController', 'updateValueByCode', '[\"SYS_OSS_CONFIG_KEY\", \"{\\\"aliyunAccessKeyId\\\":\\\"LTAI5tLJKshMDHiGVxeatCaR\\\",\\\"aliyunAccessKeySecret\\\":\\\"IC5623RxiFT1tqOrbndYwvKTnDK3vL\\\",\\\"aliyunBucketName\\\":\\\"zuiwanjia-images\\\",\\\"aliyunDomain\\\":\\\"https://pzdsoss.pzds.com\\\",\\\"aliyunEndPoint\\\":\\\"https://oss-cn-chengdu.aliyuncs.com\\\",\\\"localDomain\\\":\\\"D:/uploadFile/\\\",\\\"localPath\\\":\\\"D:/uploadFile/\\\",\\\"qcloudAppId\\\":1300679084,\\\"qcloudBucketName\\\":\\\"echo20\\\",\\\"qcloudDomain\\\":\\\"https://echo20-1300679084.cos.ap-chengdu.myqcloud.com\\\",\\\"qcloudRegion\\\":\\\"ap-chengdu\\\",\\\"qcloudSecretId\\\":\\\"AKIDwkXCdpdKp57wWs0tYyAKFuKUTHuQlvqZ\\\",\\\"qcloudSecretKey\\\":\\\"hDWbdvvqgKmz4zg9VhgPxJQIyJRTM2Xp\\\",\\\"qiniuAccessKey\\\":\\\"3TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS\\\",\\\"qiniuBucketName\\\":\\\"ruoyi-vue-pro\\\",\\\"qiniuDomain\\\":\\\"http://test.yudao.iocoder.cn\\\",\\\"qiniuSecretKey\\\":\\\"wd0tbVBYlp0S-ihA8Qg2hPLncoP83wyrIq24OZuY\\\",\\\"type\\\":2,\\\"directoryList\\\":\\\"goods\\\",\\\"minioEndPoint\\\":\\\"http://192.168.56.10:9090\\\",\\\"minioAccessKey\\\":\\\"1ONPOssH5F2HGzRCiitp\\\",\\\"minioSecretKey\\\":\\\"ewhKA8Axw5RJWGECoCPAZI2NVIoFu6cViNhhcKcH\\\",\\\"minioBucketName\\\":\\\"test-bucket\\\"}\"]', '82', '192.168.56.1', '全球', '根据参数编码，更新参数值', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-09-20 14:00:00', '2024-09-20 14:00:00');
INSERT INTO `t_operate_log` VALUES (202, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"Yrt4Of32IO6FIU41IAtLdBKa5KsGupc5r4aMKqdnKhteo3WXQ4u8MlJVE6sIfR5fsIm4ebdKGYVIZGI6UB6DLw==\"}', '1121', '192.168.56.1', '全球', '用户登录', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-09-24 17:19:39', '2024-09-24 17:19:39');
INSERT INTO `t_operate_log` VALUES (203, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"LDKI7fr8nqSgw8qjd3FiiB4yv5eVX72MqlCzkKvxfGlfwlM+Lc6RY6uUG85j3h3ilEt6Bspo7iNMozGw3hIKzA==\"}', '900', '192.168.56.1', '全球', '用户登录', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-09-30 12:21:29', '2024-09-30 12:21:29');
INSERT INTO `t_operate_log` VALUES (204, 1, 'admin', '/iris/system/config/update/code/value', 'GET', 'com.iris.blog.controller.system.SysConfigController', 'updateValueByCode', '[\"SYS_OSS_CONFIG_KEY\", \"{\\\"aliyunAccessKeyId\\\":\\\"LTAI5tLJKshMDHiGVxeatCaR\\\",\\\"aliyunAccessKeySecret\\\":\\\"IC5623RxiFT1tqOrbndYwvKTnDK3vL\\\",\\\"aliyunBucketName\\\":\\\"zuiwanjia-images\\\",\\\"aliyunDomain\\\":\\\"https://pzdsoss.pzds.com\\\",\\\"aliyunEndPoint\\\":\\\"https://oss-cn-chengdu.aliyuncs.com\\\",\\\"localDomain\\\":\\\"D:/uploadFile/\\\",\\\"localPath\\\":\\\"D:/uploadFile/\\\",\\\"qcloudAppId\\\":1300679084,\\\"qcloudBucketName\\\":\\\"echo20\\\",\\\"qcloudDomain\\\":\\\"https://echo20-1300679084.cos.ap-chengdu.myqcloud.com\\\",\\\"qcloudRegion\\\":\\\"ap-chengdu\\\",\\\"qcloudSecretId\\\":\\\"AKIDwkXCdpdKp57wWs0tYyAKFuKUTHuQlvqZ\\\",\\\"qcloudSecretKey\\\":\\\"hDWbdvvqgKmz4zg9VhgPxJQIyJRTM2Xp\\\",\\\"qiniuAccessKey\\\":\\\"3TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS\\\",\\\"qiniuBucketName\\\":\\\"ruoyi-vue-pro\\\",\\\"qiniuDomain\\\":\\\"http://test.yudao.iocoder.cn\\\",\\\"qiniuSecretKey\\\":\\\"wd0tbVBYlp0S-ihA8Qg2hPLncoP83wyrIq24OZuY\\\",\\\"type\\\":5,\\\"directoryList\\\":\\\"goods\\\",\\\"minioEndPoint\\\":\\\"http://192.168.56.10:9090\\\",\\\"minioAccessKey\\\":\\\"1ONPOssH5F2HGzRCiitp\\\",\\\"minioSecretKey\\\":\\\"ewhKA8Axw5RJWGECoCPAZI2NVIoFu6cViNhhcKcH\\\",\\\"minioBucketName\\\":\\\"test-bucket\\\"}\"]', '53', '192.168.56.1', '全球', '根据参数编码，更新参数值', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-09-30 12:30:00', '2024-09-30 12:30:00');
INSERT INTO `t_operate_log` VALUES (205, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"LbeHk2NUttHCEKsCcI2cFd5VSzxQ+kKLWyr5/we98SjGWUk8J+tm1aRFGsL3oD/KF81qhDqKvmErzWlJOXuIjA==\"}', '156', '192.168.56.1', '全球', '用户登录', 'Chrome 127-Windows 10', NULL, NULL, 0, '2024-10-10 16:50:23', '2024-10-10 16:50:23');
INSERT INTO `t_operate_log` VALUES (206, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"/rys/+4MqUXS5A83b+nWb8IU9YpXClLcUCVCZvOFMwuBaRetBl4VyaRyXq/JwS02J3aQCUCh8RwEqDysg55YRQ==\"}', '1577', '192.168.56.1', '全球', '用户登录', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 11:05:06', '2024-10-29 11:05:06');
INSERT INTO `t_operate_log` VALUES (207, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"Q+ixBWsjc2uZOXqXCKah5fNjGmECcIpATq0NzFPzfJq+4sWHSnRq8fguR8DkQB2JJkAvtPL6FPkTaVkOiXXfWQ==\"}', '1393', '192.168.56.1', '全球', '用户登录', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 13:14:18', '2024-10-29 13:14:18');
INSERT INTO `t_operate_log` VALUES (208, 1, 'admin', '/iris/system/article/save', 'POST', 'com.iris.blog.controller.system.ArticleController', 'saveArticle', '[{\"intro\": \"cesa\", \"isTop\": false, \"title\": \"ces\", \"status\": 1, \"content\": \"afasfas\", \"readType\": false, \"isForward\": true, \"coverImage\": \"https://pzdsoss.pzds.com/20241029/network_tool_00.png\", \"forwardUrl\": \"\", \"isRecommend\": true, \"tagNameList\": [\"Java\"], \"categoryName\": \"踩坑记\"}]', '227', '192.168.56.1', '全球', '保存文章', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 13:30:00', '2024-10-29 13:30:00');
INSERT INTO `t_operate_log` VALUES (209, 1, 'admin', '/iris/system/config/update/code/value', 'GET', 'com.iris.blog.controller.system.SysConfigController', 'updateValueByCode', '[\"SYS_OSS_CONFIG_KEY\", \"{\\\"aliyunAccessKeyId\\\":\\\"LTAI5tLJKshMDHiGVxeatCaR\\\",\\\"aliyunAccessKeySecret\\\":\\\"IC5623RxiFT1tqOrbndYwvKTnDK3vL\\\",\\\"aliyunBucketName\\\":\\\"zuiwanjia-images\\\",\\\"aliyunDomain\\\":\\\"https://pzdsoss.pzds.com\\\",\\\"aliyunEndPoint\\\":\\\"https://oss-cn-chengdu.aliyuncs.com\\\",\\\"localDomain\\\":\\\"D:/uploadFile/\\\",\\\"localPath\\\":\\\"D:/uploadFile/\\\",\\\"qcloudAppId\\\":1300679084,\\\"qcloudBucketName\\\":\\\"echo20\\\",\\\"qcloudDomain\\\":\\\"https://echo20-1300679084.cos.ap-chengdu.myqcloud.com\\\",\\\"qcloudRegion\\\":\\\"ap-chengdu\\\",\\\"qcloudSecretId\\\":\\\"AKIDwkXCdpdKp57wWs0tYyAKFuKUTHuQlvqZ\\\",\\\"qcloudSecretKey\\\":\\\"hDWbdvvqgKmz4zg9VhgPxJQIyJRTM2Xp\\\",\\\"qiniuAccessKey\\\":\\\"3TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS\\\",\\\"qiniuBucketName\\\":\\\"ruoyi-vue-pro\\\",\\\"qiniuDomain\\\":\\\"http://test.yudao.iocoder.cn\\\",\\\"qiniuSecretKey\\\":\\\"wd0tbVBYlp0S-ihA8Qg2hPLncoP83wyrIq24OZuY\\\",\\\"type\\\":1,\\\"directoryList\\\":\\\"goods\\\",\\\"minioEndPoint\\\":\\\"http://192.168.56.10:9090\\\",\\\"minioAccessKey\\\":\\\"1ONPOssH5F2HGzRCiitp\\\",\\\"minioSecretKey\\\":\\\"ewhKA8Axw5RJWGECoCPAZI2NVIoFu6cViNhhcKcH\\\",\\\"minioBucketName\\\":\\\"test-bucket\\\"}\"]', '61', '192.168.56.1', '全球', '根据参数编码，更新参数值', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 13:30:00', '2024-10-29 13:30:00');
INSERT INTO `t_operate_log` VALUES (210, 1, 'admin', '/iris/system/article/update', 'POST', 'com.iris.blog.controller.system.ArticleController', 'updateArticle', '[{\"id\": 9, \"intro\": \"cesa\", \"isTop\": false, \"title\": \"ces\", \"status\": 1, \"content\": \"afasfas\", \"readType\": false, \"isForward\": true, \"coverImage\": \"https://admin.lstar.icu:9090/file/image/20241029/image.png\", \"createTime\": \"2024-10-29T13:18:50\", \"forwardUrl\": \"\", \"updateTime\": \"2024-10-29T13:18:50\", \"isRecommend\": true, \"tagNameList\": [\"Java\"], \"categoryName\": \"踩坑记\"}]', '273', '192.168.56.1', '全球', '修改文章', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 13:30:00', '2024-10-29 13:30:00');
INSERT INTO `t_operate_log` VALUES (211, 1, 'admin', '/iris/system/config/update/code/value', 'GET', 'com.iris.blog.controller.system.SysConfigController', 'updateValueByCode', '[\"SYS_OSS_CONFIG_KEY\", \"{\\\"aliyunAccessKeyId\\\":\\\"LTAI5tLJKshMDHiGVxeatCaR\\\",\\\"aliyunAccessKeySecret\\\":\\\"IC5623RxiFT1tqOrbndYwvKTnDK3vL\\\",\\\"aliyunBucketName\\\":\\\"zuiwanjia-images\\\",\\\"aliyunDomain\\\":\\\"https://pzdsoss.pzds.com\\\",\\\"aliyunEndPoint\\\":\\\"https://oss-cn-chengdu.aliyuncs.com\\\",\\\"localDomain\\\":\\\"D:/uploadFile/\\\",\\\"localPath\\\":\\\"D:/uploadFile/\\\",\\\"qcloudAppId\\\":1300679084,\\\"qcloudBucketName\\\":\\\"echo20\\\",\\\"qcloudDomain\\\":\\\"https://echo20-1300679084.cos.ap-chengdu.myqcloud.com\\\",\\\"qcloudRegion\\\":\\\"ap-chengdu\\\",\\\"qcloudSecretId\\\":\\\"AKIDwkXCdpdKp57wWs0tYyAKFuKUTHuQlvqZ\\\",\\\"qcloudSecretKey\\\":\\\"hDWbdvvqgKmz4zg9VhgPxJQIyJRTM2Xp\\\",\\\"qiniuAccessKey\\\":\\\"3TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS\\\",\\\"qiniuBucketName\\\":\\\"ruoyi-vue-pro\\\",\\\"qiniuDomain\\\":\\\"http://test.yudao.iocoder.cn\\\",\\\"qiniuSecretKey\\\":\\\"wd0tbVBYlp0S-ihA8Qg2hPLncoP83wyrIq24OZuY\\\",\\\"type\\\":4,\\\"directoryList\\\":\\\"goods\\\",\\\"minioEndPoint\\\":\\\"http://192.168.56.10:9090\\\",\\\"minioAccessKey\\\":\\\"1ONPOssH5F2HGzRCiitp\\\",\\\"minioSecretKey\\\":\\\"ewhKA8Axw5RJWGECoCPAZI2NVIoFu6cViNhhcKcH\\\",\\\"minioBucketName\\\":\\\"test-bucket\\\"}\"]', '57', '192.168.56.1', '全球', '根据参数编码，更新参数值', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 13:30:00', '2024-10-29 13:30:00');
INSERT INTO `t_operate_log` VALUES (212, 1, 'admin', '/iris/system/article/update', 'POST', 'com.iris.blog.controller.system.ArticleController', 'updateArticle', '[{\"id\": 9, \"intro\": \"cesa\", \"isTop\": false, \"title\": \"ces\", \"status\": 1, \"content\": \"afasfas\", \"readType\": false, \"isForward\": true, \"coverImage\": \"https://admin.lstar.icu:9090/file/image/20241029/image.png\", \"createTime\": \"2024-10-29T13:18:50\", \"forwardUrl\": \"\", \"updateTime\": \"2024-10-29T13:18:50\", \"isRecommend\": true, \"tagNameList\": [\"Java\"], \"categoryName\": \"踩坑记\"}]', '261', '192.168.56.1', '全球', '修改文章', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 13:30:00', '2024-10-29 13:30:00');
INSERT INTO `t_operate_log` VALUES (213, 1, 'admin', '/iris/system/article/update', 'POST', 'com.iris.blog.controller.system.ArticleController', 'updateArticle', '[{\"id\": 9, \"intro\": \"cesa\", \"isTop\": true, \"title\": \"ces\", \"status\": 1, \"content\": \"afasfas\", \"readType\": false, \"isForward\": true, \"coverImage\": \"https://admin.lstar.icu:9090/file/image/20241029/image.png\", \"createTime\": \"2024-10-29T13:18:50\", \"forwardUrl\": \"\", \"updateTime\": \"2024-10-29T13:18:50\", \"isRecommend\": true, \"tagNameList\": [\"Java\"], \"categoryName\": \"踩坑记\"}]', '275', '192.168.56.1', '全球', '修改文章', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 14:00:00', '2024-10-29 14:00:00');
INSERT INTO `t_operate_log` VALUES (214, 1, 'admin', '/iris/system/article/update', 'POST', 'com.iris.blog.controller.system.ArticleController', 'updateArticle', '[{\"id\": 9, \"intro\": \"cesa\", \"isTop\": true, \"title\": \"ces\", \"status\": 1, \"content\": \"afasfas\", \"readType\": false, \"isForward\": true, \"coverImage\": \"https://admin.lstar.icu:9090/file/image/20241029/image.png\", \"createTime\": \"2024-10-29T13:18:50\", \"forwardUrl\": \"\", \"updateTime\": \"2024-10-29T13:18:50\", \"isRecommend\": true, \"tagNameList\": [\"Java\"], \"categoryName\": \"踩坑记\"}]', '12676', '192.168.56.1', '全球', '修改文章', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 14:00:00', '2024-10-29 14:00:00');
INSERT INTO `t_operate_log` VALUES (215, 1, 'admin', '/iris/system/article/update', 'POST', 'com.iris.blog.controller.system.ArticleController', 'updateArticle', '[{\"id\": 9, \"intro\": \"cesa\", \"isTop\": true, \"title\": \"ces\", \"status\": 1, \"content\": \"afasfas\", \"readType\": false, \"isForward\": true, \"coverImage\": \"https://admin.lstar.icu:9090/file/image/20241029/image.png\", \"createTime\": \"2024-10-29T13:18:50\", \"forwardUrl\": \"\", \"updateTime\": \"2024-10-29T13:18:50\", \"isRecommend\": true, \"tagNameList\": [\"Java\"], \"categoryName\": \"踩坑记\"}]', '2175', '192.168.56.1', '全球', '修改文章', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 14:00:00', '2024-10-29 14:00:00');
INSERT INTO `t_operate_log` VALUES (216, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"yw9sUAQ6HS550kcQaQh3/DPMczcIDLRWTymsWSQLg8W1Rkhut8SAyaoirM1FkCNqhdHC63j1z8UCABNeykFlpA==\"}', '1045', '192.168.56.1', '全球', '用户登录', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 14:17:56', '2024-10-29 14:17:56');
INSERT INTO `t_operate_log` VALUES (217, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"WgX1YKOlwwDwNYqzllAM20ojptkrBoS69pt39V7CNbtZPAPzUo/J0FYifZ/idhFdkDe4mMBQjzNjF7D38NosMA==\"}', '929', '192.168.56.1', '全球', '用户登录', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 15:05:32', '2024-10-29 15:05:32');
INSERT INTO `t_operate_log` VALUES (218, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"edMOWNEPIxmlITZrLCioXmRPJxlSoZ7ElVsbAHZZdf7EqjkxHXVnJbaL+hr5/HEOymr4a1fgipxm1WJmsUpFAQ==\"}', '1469', '192.168.56.1', '全球', '用户登录', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 15:22:16', '2024-10-29 15:22:16');
INSERT INTO `t_operate_log` VALUES (219, 1, 'admin', '/iris/system/login/login', 'POST', 'com.iris.blog.controller.system.LoginController', 'login', '{\"password\": \"123456\", \"username\": \"admin\", \"rememberMe\": true, \"captchaCode\": \"f5LkFLbnNEWDMvDrffKXhTPbqZYek9qslCtiNjfe2d8To4MK/0e+3otfIjBNPzkAb1ZJ9Z+qUiJWdtYR/2VEGA==\"}', '757', '192.168.56.1', '全球', '用户登录', 'Chrome 122-Windows 10', NULL, NULL, 0, '2024-10-29 17:57:47', '2024-10-29 17:57:47');

-- ----------------------------
-- Table structure for t_oss_file
-- ----------------------------
DROP TABLE IF EXISTS `t_oss_file`;
CREATE TABLE `t_oss_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件URL',
  `file_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `config_type` tinyint(3) NULL DEFAULT NULL COMMENT '配置类型',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_oss_file
-- ----------------------------
INSERT INTO `t_oss_file` VALUES (83, 'network_tool_01.png', 'D:/uploadFile//20241029/network_tool_01.png', '20241029/network_tool_01.png', 'png', 41185, 4, '1', '2024-10-29 13:29:39');
INSERT INTO `t_oss_file` VALUES (84, 'network_tool_00.png', 'D:/uploadFile//20241029/network_tool_00.png', '20241029/network_tool_00.png', 'png', 87239, 4, '1', '2024-10-29 13:29:39');
INSERT INTO `t_oss_file` VALUES (85, 'network_tool_02.png', 'D:/uploadFile//20241029/network_tool_02.png', '20241029/network_tool_02.png', 'png', 57440, 4, '1', '2024-10-29 13:29:39');
INSERT INTO `t_oss_file` VALUES (86, 'network_tool_03.png', 'D:/uploadFile//20241029/network_tool_03.png', '20241029/network_tool_03.png', 'png', 55563, 4, '1', '2024-10-29 13:29:39');
INSERT INTO `t_oss_file` VALUES (87, 'network_tool_04.png', 'D:/uploadFile//20241029/network_tool_04.png', '20241029/network_tool_04.png', 'png', 41383, 4, '1', '2024-10-29 13:29:39');

-- ----------------------------
-- Table structure for t_report_center
-- ----------------------------
DROP TABLE IF EXISTS `t_report_center`;
CREATE TABLE `t_report_center`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报表名称',
  `service_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务名称',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态 0处理中 1已完成 -1失败',
  `duration` bigint(20) NULL DEFAULT NULL COMMENT '处理时长',
  `oss_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报表地址',
  `file_size` int(12) NULL DEFAULT NULL COMMENT '文件大小bytes',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url地址',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `suffix` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后缀',
  `result` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下载结果',
  `operator_id` bigint(20) NOT NULL COMMENT '操作人id',
  `operator_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人名字',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_status_create_time`(`status`, `create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报表下载中心' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_report_center
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `remarks` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'super_admin', '超级管理员', '超级管理员', '2024-05-08 11:30:04', '2024-05-08 11:30:04');
INSERT INTO `t_role` VALUES (2, 'common', '普通角色', '普通角色', '2024-05-08 11:30:24', '2024-05-08 11:30:24');
INSERT INTO `t_role` VALUES (3, 'test', '测试', '测试', '2024-05-16 13:55:56', '2024-05-30 15:14:43');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_id`(`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 234 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色-权限关联表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (14, 1, 14, '2024-05-30 15:20:15', '2024-05-30 15:21:11');
INSERT INTO `t_role_menu` VALUES (51, 4, 49, '2024-05-30 15:23:10', '2024-05-30 15:23:10');
INSERT INTO `t_role_menu` VALUES (52, 4, 50, '2024-05-30 15:23:10', '2024-05-30 15:23:10');
INSERT INTO `t_role_menu` VALUES (53, 4, 2, '2024-05-30 15:23:10', '2024-05-30 15:23:10');
INSERT INTO `t_role_menu` VALUES (54, 2, 29, '2024-05-30 15:23:29', '2024-05-30 15:23:29');
INSERT INTO `t_role_menu` VALUES (55, 2, 30, '2024-05-30 15:23:29', '2024-05-30 15:23:29');
INSERT INTO `t_role_menu` VALUES (56, 2, 31, '2024-05-30 15:23:29', '2024-05-30 15:23:29');
INSERT INTO `t_role_menu` VALUES (57, 4, 54, '2024-06-11 13:13:03', '2024-06-11 13:13:03');
INSERT INTO `t_role_menu` VALUES (58, 4, 55, '2024-06-11 13:13:03', '2024-06-11 13:13:03');
INSERT INTO `t_role_menu` VALUES (59, 4, 56, '2024-06-11 13:13:03', '2024-06-11 13:13:03');
INSERT INTO `t_role_menu` VALUES (60, 4, 57, '2024-06-11 13:13:03', '2024-06-11 13:13:03');
INSERT INTO `t_role_menu` VALUES (61, 4, 58, '2024-06-11 13:13:03', '2024-06-11 13:13:03');
INSERT INTO `t_role_menu` VALUES (62, 4, 59, '2024-06-11 13:13:03', '2024-06-11 13:13:03');
INSERT INTO `t_role_menu` VALUES (63, 4, 60, '2024-06-11 13:13:03', '2024-06-11 13:13:03');
INSERT INTO `t_role_menu` VALUES (64, 4, 61, '2024-06-11 13:13:03', '2024-06-11 13:13:03');
INSERT INTO `t_role_menu` VALUES (65, 4, 62, '2024-06-11 13:13:03', '2024-06-11 13:13:03');
INSERT INTO `t_role_menu` VALUES (66, 4, 63, '2024-06-11 13:13:03', '2024-06-11 13:13:03');
INSERT INTO `t_role_menu` VALUES (114, 1, 1, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (115, 1, 2, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (116, 1, 3, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (117, 1, 4, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (118, 1, 5, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (119, 1, 6, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (120, 1, 7, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (121, 1, 8, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (122, 1, 9, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (123, 1, 10, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (124, 1, 11, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (125, 1, 12, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (126, 1, 13, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (127, 1, 15, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (128, 1, 16, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (129, 1, 17, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (130, 1, 18, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (131, 1, 19, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (132, 1, 20, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (133, 1, 21, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (134, 1, 22, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (135, 1, 23, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (136, 1, 24, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (137, 1, 25, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (138, 1, 26, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (139, 1, 27, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (140, 1, 28, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (141, 1, 29, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (142, 1, 30, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (143, 1, 31, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (144, 1, 32, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (145, 1, 33, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (146, 1, 34, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (147, 1, 35, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (148, 1, 36, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (149, 1, 37, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (150, 1, 38, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (151, 1, 39, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (152, 1, 40, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (153, 1, 41, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (154, 1, 42, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (155, 1, 43, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (156, 1, 44, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (157, 1, 45, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (158, 1, 46, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (159, 1, 47, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (160, 1, 48, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (161, 1, 49, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (162, 1, 50, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (163, 1, 52, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (164, 1, 53, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (165, 1, 54, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (166, 1, 55, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (167, 1, 56, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (168, 1, 57, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (169, 1, 58, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (170, 1, 59, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (171, 1, 60, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (172, 1, 61, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (173, 1, 62, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (174, 1, 63, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (175, 1, 64, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (176, 1, 65, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (177, 1, 66, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (178, 1, 67, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (179, 1, 68, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (180, 1, 69, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (181, 1, 70, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (182, 1, 71, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (183, 1, 72, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (184, 1, 73, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (185, 1, 74, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (186, 1, 75, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (187, 1, 76, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (188, 1, 77, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (189, 1, 78, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (190, 1, 79, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (191, 1, 80, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (192, 1, 81, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (193, 1, 82, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (194, 1, 83, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (195, 1, 84, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (196, 1, 85, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (197, 1, 86, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (198, 1, 87, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (199, 1, 88, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (200, 1, 89, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (201, 1, 90, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (202, 1, 91, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (203, 1, 92, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (204, 1, 93, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (205, 1, 94, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (206, 1, 95, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (207, 1, 96, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (208, 1, 97, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (209, 1, 98, '2024-07-01 15:01:03', '2024-07-01 15:01:03');
INSERT INTO `t_role_menu` VALUES (210, 2, 1, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (211, 2, 3, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (212, 2, 4, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (213, 2, 9, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (214, 2, 10, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (215, 2, 11, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (216, 2, 12, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (217, 2, 13, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (218, 2, 14, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (219, 2, 15, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (220, 2, 16, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (221, 2, 17, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (222, 2, 18, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (223, 2, 23, '2024-07-01 15:01:37', '2024-07-01 15:01:37');
INSERT INTO `t_role_menu` VALUES (224, 1, 99, '2024-08-02 17:58:55', '2024-08-02 17:58:55');
INSERT INTO `t_role_menu` VALUES (225, 1, 100, '2024-08-02 17:58:55', '2024-08-02 17:58:55');
INSERT INTO `t_role_menu` VALUES (226, 1, 101, '2024-08-02 17:58:55', '2024-08-02 17:58:55');
INSERT INTO `t_role_menu` VALUES (227, 1, 102, '2024-08-02 17:58:55', '2024-08-02 17:58:55');
INSERT INTO `t_role_menu` VALUES (228, 1, 103, '2024-08-02 17:58:55', '2024-08-02 17:58:55');
INSERT INTO `t_role_menu` VALUES (229, 1, 104, '2024-08-05 13:15:42', '2024-08-05 13:15:42');
INSERT INTO `t_role_menu` VALUES (230, 1, 105, '2024-08-05 13:15:42', '2024-08-05 13:15:42');
INSERT INTO `t_role_menu` VALUES (231, 1, 106, '2024-08-05 13:15:42', '2024-08-05 13:15:42');
INSERT INTO `t_role_menu` VALUES (232, 1, 107, '2024-08-05 13:15:42', '2024-08-05 13:15:42');
INSERT INTO `t_role_menu` VALUES (233, 1, 108, '2024-08-05 13:15:42', '2024-08-05 13:15:42');
INSERT INTO `t_role_menu` VALUES (234, 1, 109, '2025-01-22 14:57:49', '2025-01-22 14:57:49');

-- ----------------------------
-- Table structure for t_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule_job`;
CREATE TABLE `t_schedule_job`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` tinyint(3) NULL DEFAULT 3 COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`, `job_name`, `job_group`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_schedule_job
-- ----------------------------
INSERT INTO `t_schedule_job` VALUES (10, '系统默认有参', 'DEFAULT', 'consumerTask.ryParams(\'ry\')', '0/10 * * * * ?', 3, 0, 'admin', '', '测试', '2024-04-23 16:40:21', '2024-05-21 09:58:23');
INSERT INTO `t_schedule_job` VALUES (11, '定时保存系统日志', 'DEFAULT', 'consumerTask.operateLogConsumer()', '0 0/30 * * * ?', 3, 1, 'admin', 'admin', '测试', '2024-04-23 17:11:58', '2024-05-21 09:58:26');
INSERT INTO `t_schedule_job` VALUES (12, '无参定时器', '默认', 'consumerTask.ryNoParams()', '0/5 * * * * ?', 1, 0, 'admin', 'admin', '', '2024-05-21 11:48:37', '2024-05-21 11:48:37');
INSERT INTO `t_schedule_job` VALUES (22, '解析Nginx日志', 'DEFAULT', 'consumerTask.parseNginxLog(1L)', '0 0/5 * * * ?', 3, 1, 'admin', 'admin', '', '2024-06-13 14:37:52', '2024-06-13 14:37:52');

-- ----------------------------
-- Table structure for t_schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule_job_log`;
CREATE TABLE `t_schedule_job_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_id` bigint(20) NOT NULL COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `stop_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `duration` int(10) NOT NULL DEFAULT 0 COMMENT '执行时长',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_job_id`(`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6873 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_schedule_job_log
-- ----------------------------
INSERT INTO `t_schedule_job_log` VALUES (3009, 22, '解析Nginx日志', 'DEFAULT', 'consumerTask.parseNginxLog(1L)', '解析Nginx日志 总共耗时：20毫秒', 0, '', '2024-07-05 20:10:00', '2024-07-05 20:10:00', '2024-07-05 20:10:00', '2024-07-05 20:10:00', 20);
INSERT INTO `t_schedule_job_log` VALUES (3010, 22, '解析Nginx日志', 'DEFAULT', 'consumerTask.parseNginxLog(1L)', '解析Nginx日志 总共耗时：8毫秒', 0, '', '2024-07-05 20:15:00', '2024-07-05 20:15:00', '2024-07-05 20:15:00', '2024-07-05 20:15:00', 8);
INSERT INTO `t_schedule_job_log` VALUES (6914, 11, '定时保存系统日志', 'DEFAULT', 'consumerTask.operateLogConsumer()', '定时保存系统日志 总共耗时：8毫秒', 0, '', '2024-10-29 18:00:00', '2024-10-29 18:00:00', '2024-10-29 18:00:00', '2024-10-29 18:00:00', 8);
INSERT INTO `t_schedule_job_log` VALUES (6915, 22, '解析Nginx日志', 'DEFAULT', 'consumerTask.parseNginxLog(1L)', '解析Nginx日志 总共耗时：17毫秒', 0, '', '2024-10-29 18:00:00', '2024-10-29 18:00:00', '2024-10-29 18:00:00', '2024-10-29 18:00:00', 17);
INSERT INTO `t_schedule_job_log` VALUES (6916, 22, '解析Nginx日志', 'DEFAULT', 'consumerTask.parseNginxLog(1L)', '解析Nginx日志 总共耗时：8毫秒', 0, '', '2024-10-29 18:05:00', '2024-10-29 18:05:00', '2024-10-29 18:05:00', '2024-10-29 18:05:00', 8);
INSERT INTO `t_schedule_job_log` VALUES (6917, 22, '解析Nginx日志', 'DEFAULT', 'consumerTask.parseNginxLog(1L)', '解析Nginx日志 总共耗时：1519毫秒', 0, '', '2024-10-29 18:10:00', '2024-10-29 18:10:02', '2024-10-29 18:10:02', '2024-10-29 18:10:02', 1519);
INSERT INTO `t_schedule_job_log` VALUES (6918, 22, '解析Nginx日志', 'DEFAULT', 'consumerTask.parseNginxLog(1L)', '解析Nginx日志 总共耗时：17毫秒', 0, '', '2024-10-29 18:15:00', '2024-10-29 18:15:00', '2024-10-29 18:15:00', '2024-10-29 18:15:00', 17);

-- ----------------------------
-- Table structure for t_site_config
-- ----------------------------
DROP TABLE IF EXISTS `t_site_config`;
CREATE TABLE `t_site_config`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户头像',
  `tourist_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '游客头像',
  `site_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站名称',
  `site_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站地址',
  `site_intro` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站简介',
  `site_notice` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站公告',
  `create_site_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '建站日期',
  `record_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备案号',
  `author_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作者头像',
  `site_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站作者',
  `article_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章默认封面',
  `about_me` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '关于我',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `github` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Github',
  `gitee` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Gitee',
  `qq_group` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QQ群',
  `qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `comment_check` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否评论审核 (0否 1是)',
  `message_check` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否留言审核 (0否 1是)',
  `is_reward` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启打赏 (0否 1是)',
  `wechat_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信二维码',
  `alipay_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付宝二维码',
  `email_notice` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否邮箱通知 (0否 1是)',
  `social_list` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '社交列表',
  `login_list` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录方式',
  `is_music` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启音乐播放器 (0否 1是)',
  `music_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网易云歌单id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网站配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_site_config
-- ----------------------------
INSERT INTO `t_site_config` VALUES (1, 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '鸢尾博客', 'https://www.lstar.icu', '网站介绍', '后端基于SpringBoot开发，前端基于Vue3 Ts Navie UI开发，<a href=\"https://gitee.com/lxwise/iris-blog_parent\" style=\"color: #49b1f5;\">网站源码</a>', '2022-08-25', '蜀ICP备2024067985号-1', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '鸢尾博客', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', '🍀个人简介\n\n一只努力成为成为技术大牛的菜鸟程序员', '1444073716@qq.com', 'https://github.com/lxwise', 'https://gitee.com/lxwise', '1444073716', '1444073716', 1, 1, 1, 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 1, 'email,github,gitee,qq,qqGroup', 'gitee,github,qq,weibo', 1, '7611185981', '2023-01-07 19:31:33', '2024-06-27 17:42:15');

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `param_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数编码',
  `param_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '参数值',
  `param_type` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '类型   0：系统参数   1：非系统参数',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '状态  0：不使用    1：使用',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_param_code`(`param_code`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
INSERT INTO `t_sys_config` VALUES (1, 'SYS_OSS_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"*\",\"aliyunAccessKeySecret\":\"*\",\"aliyunBucketName\":\"*\",\"aliyunDomain\":\"*\",\"aliyunEndPoint\":\"*\",\"localDomain\":\"D:/uploadFile/\",\"localPath\":\"D:/uploadFile/\",\"qcloudAppId\":123,\"qcloudBucketName\":\"123\",\"qcloudDomain\":\"*\",\"qcloudRegion\":\"*\",\"qcloudSecretId\":\"*\",\"qcloudSecretKey\":\"*\",\"qiniuAccessKey\":\"*\",\"qiniuBucketName\":\"*\",\"qiniuDomain\":\"*\",\"qiniuSecretKey\":\"*\",\"type\":4,\"directoryList\":\"goods\",\"minioEndPoint\":\"http://192.168.56.10:9090\",\"minioAccessKey\":\"1ONPOssH5F2HGzRCiitp\",\"minioSecretKey\":\"ewhKA8Axw5RJWGECoCPAZI2NVIoFu6cViNhhcKcH\",\"minioBucketName\":\"test-bucket\"}', 0, '云存储配置', 1, '2024-05-23 16:41:00', '2024-10-29 17:54:19');
INSERT INTO `t_sys_config` VALUES (3, 'SYS_MAIL_CONFIG_KEY', '{\"emailHost\":\"smtp.qq.com\",\"emailUsername\":\"123456@qq.com\",\"emailPassword\":\"123456\",\"emailPort\":\"587\"}', 0, '邮件服务:emailHost:邮箱地址,emailUsername:邮箱发件人,emailPassword:邮箱授权码,emailPort:邮箱端口', 1, '2024-09-14 11:29:50', '2024-10-29 17:54:39');

-- ----------------------------
-- Table structure for t_talk
-- ----------------------------
DROP TABLE IF EXISTS `t_talk`;
CREATE TABLE `t_talk`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '说说id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `talk_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '说说内容',
  `is_top` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶 (0否 1是)',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 (1公开  2私密)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '说说' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_talk
-- ----------------------------
INSERT INTO `t_talk` VALUES (1, 1, '<p>这是测试说说🙁,asdad,</p>', 1, 1, '2024-08-02 18:03:08', '2024-09-10 17:39:56');
INSERT INTO `t_talk` VALUES (2, 1, '<p>这是测试说说123🙁,asdad,</p>', 0, 1, '2024-08-02 18:05:42', '2024-09-10 17:52:33');
INSERT INTO `t_talk` VALUES (3, 1, '<p>进度终于快一半啦👀</p>', 1, 1, '2024-09-13 10:17:42', '2024-10-29 17:54:53');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号/用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 0:禁用 1:正常',
  `login_type` int(10) NULL DEFAULT NULL COMMENT '登录方式1邮箱,2QQ,3微博,4码云,5github',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `region` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `latitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `longitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经度',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `last_login_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最后登录时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', 'Mif5kcfDHH7SS6hI6PyWRw==', 1, 1, '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 'Chrome 122-Windows 10', '2024-10-29 17:57:47', '2024-04-23 18:35:36', '2024-10-29 17:57:46');
INSERT INTO `t_user` VALUES (2, '测试', 'Mif5kcfDHH7SS6hI6PyWRw==', 1, NULL, '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 'Chrome 126-Windows 10', '2024-07-01 15:02:13', '2024-05-14 16:35:24', '2024-07-01 15:02:12');
INSERT INTO `t_user` VALUES (3, '测试001', 'Mif5kcfDHH7SS6hI6PyWRw==', 1, NULL, '192.168.56.1', '全球', NULL, NULL, NULL, NULL, NULL, 'Chrome 126-Windows 10', '2024-07-01 15:04:14', '2024-05-16 11:15:49', '2024-07-01 15:04:13');
INSERT INTO `t_user` VALUES (16, '1@qq.com', 'Mif5kcfDHH7SS6hI6PyWRw==', 1, 1, '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 'Chrome 127-Windows 10', '2024-09-30 10:43:23', '2024-09-30 09:22:24', '2024-09-30 10:43:22');
INSERT INTO `t_user` VALUES (17, '2@qq.com', 'Mif5kcfDHH7SS6hI6PyWRw==', 1, 1, '192.168.56.1', '全球', '未知', '未知', NULL, NULL, NULL, 'Chrome 122-Windows 10', '2024-09-30 17:29:07', '2024-09-30 09:23:39', '2024-09-30 17:29:06');

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info`  (
  `id` bigint(20) NOT NULL COMMENT '用户ID',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系qq',
  `wx` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系wx',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '性别:0女,1男,2未知',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `is_disable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用:0否,1是',
  `back_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人中心背景图',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_info
-- ----------------------------
INSERT INTO `t_user_info` VALUES (1, '13330333033', '1333133303', '13330333033', '1333133303@qq.com', '鸢尾', 1, '鸢尾博客,基于springboot + vue + javafx开发的前后端分离博客', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 0, 'https://pic.netbian.com/uploads/allimg/240405/223502-17123277028d9c.jpg', '2024-04-19 09:48:15', '2024-10-29 17:55:58');
INSERT INTO `t_user_info` VALUES (2, '21312312', '123123', '13312312312', '13312312312@qq.com', 'testing', 1, '啊等哈水晶湖郡', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 0, NULL, '2024-05-14 16:35:24', '2024-10-29 17:55:58');
INSERT INTO `t_user_info` VALUES (3, '13330333033', '13330333033', '13330333033', '1333033303@qq.com', '测试001', 2, '测试00112', 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 0, NULL, '2024-05-16 11:15:49', '2024-10-29 17:55:58');
INSERT INTO `t_user_info` VALUES (16, NULL, NULL, NULL, '1@qq.com', '这是评论用户', NULL, NULL, 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 0, NULL, '2024-09-30 09:22:24', '2024-10-29 17:55:58');
INSERT INTO `t_user_info` VALUES (17, NULL, NULL, NULL, '2@qq.com', '这是被评论用户', NULL, NULL, 'https://img1.baidu.com/it/u=1090452517,2487311686&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501', 0, NULL, '2024-09-30 09:23:39', '2024-10-29 17:55:58');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-用户角色关联表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1, 1, '2024-05-30 15:17:03', '2024-05-30 15:17:03');
INSERT INTO `t_user_role` VALUES (8, 2, 3, '2024-05-30 15:26:12', '2024-05-30 15:26:12');
INSERT INTO `t_user_role` VALUES (9, 4, 2, '2024-05-30 15:26:19', '2024-05-30 15:26:19');
INSERT INTO `t_user_role` VALUES (12, 1, 4, '2024-09-11 19:47:50', '2024-09-11 19:47:50');
INSERT INTO `t_user_role` VALUES (13, 1, 5, '2024-09-12 11:29:16', '2024-09-12 11:29:16');
INSERT INTO `t_user_role` VALUES (16, 2, 8, '2024-09-14 17:49:31', '2024-09-14 17:49:31');
INSERT INTO `t_user_role` VALUES (17, 2, 9, '2024-09-14 17:51:19', '2024-09-14 17:51:19');
INSERT INTO `t_user_role` VALUES (18, 2, 10, '2024-09-14 17:59:51', '2024-09-14 17:59:51');
INSERT INTO `t_user_role` VALUES (19, 2, 11, '2024-09-14 18:03:20', '2024-09-14 18:03:20');
INSERT INTO `t_user_role` VALUES (20, 2, 12, '2024-09-14 18:19:07', '2024-09-14 18:19:07');
INSERT INTO `t_user_role` VALUES (21, 2, 13, '2024-09-19 15:51:27', '2024-09-19 15:51:27');
INSERT INTO `t_user_role` VALUES (22, 2, 14, '2024-09-19 15:52:15', '2024-09-19 15:52:15');
INSERT INTO `t_user_role` VALUES (23, 2, 15, '2024-09-20 10:54:33', '2024-09-20 10:54:33');
INSERT INTO `t_user_role` VALUES (24, 2, 16, '2024-09-30 09:22:24', '2024-09-30 09:22:24');
INSERT INTO `t_user_role` VALUES (25, 2, 17, '2024-09-30 09:23:39', '2024-09-30 09:23:39');

SET FOREIGN_KEY_CHECKS = 1;
