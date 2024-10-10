// 导入 CryptoJS 模块
import CryptoJS from 'crypto-js';
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict';

/**
 * @description: 获取加密密钥
 * @return {string} 返回加密密钥
 */
const getEncryptKey = () => {
    return getStrDictOptions(DICT_TYPE.SYSTEM_ENCRYPT_KEY);
};

// 加密函数
function encrypt(data) {
    // 将不同类型的数据转换为字符串
    const dataString = JSON.stringify(data);
    // 获取加密密钥
    const key = getEncryptKey();
    // AES 加密
    const ciphertext = CryptoJS.AES.encrypt(dataString, key[0].value).toString();
    return ciphertext;
}

// 解密函数
function decrypt(ciphertext) {
    // 获取加密密钥
    const key = getEncryptKey();
    // AES 解密
    const bytes = CryptoJS.AES.decrypt(ciphertext, key[0].value);
    const decryptedString = bytes.toString(CryptoJS.enc.Utf8);
    // 将 JSON 字符串转换回原始数据
    const decryptedData = JSON.parse(decryptedString);
    return decryptedData;
}

export { encrypt, decrypt };
