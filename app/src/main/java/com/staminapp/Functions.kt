package com.staminapp

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.encoder.QRCode

fun getQrCodeBitmap(url: String): Bitmap {
    val size = 256 //pixels
    val hintMap = mapOf(EncodeHintType.MARGIN to 1)
    val code = QRCodeWriter().encode(url,BarcodeFormat.QR_CODE, size, size, hintMap)
    val bitmap = Bitmap.createBitmap(code.width, code.height, Bitmap.Config.RGB_565)
    for (x in 0 until  code.width) {
        for (y in 0 until code.height) {
            bitmap.setPixel(x, y, if (code.get(x, y)) Color.rgb(253,153,0) else Color.rgb(0,24,51))
        }
    }
    return bitmap
}