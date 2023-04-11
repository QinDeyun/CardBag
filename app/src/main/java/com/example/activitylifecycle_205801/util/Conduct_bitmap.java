package com.example.activitylifecycle_205801.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Conduct_bitmap {

    //合成两张图片
    public  static Bitmap compositing_bitmaps(Bitmap bitmap1,Bitmap bitmap2){
        // 创建一个空白的Bitmap对象
        Bitmap result = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight()+bitmap2.getHeight(), bitmap1.getConfig());
        // 创建一个Canvas对象，并将其绑定到空白Bitmap对象上
        Canvas canvas = new Canvas(result);
        // 使用Canvas对象将第一张照片和第二张照片绘制到空白Bitmap对象上
        canvas.drawBitmap(bitmap2, 0, 0, null);
        canvas.drawBitmap(bitmap1, 0, bitmap2.getHeight(), null);

        return result;

    }
    //图片添加水印
    public  static Bitmap add_watermark(Bitmap bitmap,String watermark){
        Bitmap result=bitmap;
        // 获取Bitmap对象的宽度和高度
        int width = result.getWidth();
        int height = result.getHeight();

        // 计算图片对角线的长度和角度
        float diagonalLength = (float) Math.sqrt(width * width + height * height);
        float diagonalAngle = (float) Math.toDegrees(Math.atan2(height, width));

        // 创建一个Canvas对象，并在其上绘制原始Bitmap对象
        Bitmap bitmapWithWatermark = Bitmap.createBitmap((int) diagonalLength, (int) diagonalLength, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmapWithWatermark);
        canvas2.drawBitmap(result, (diagonalLength - width) / 2, (diagonalLength - height) / 2, null);

        // 创建一个Paint对象，并设置水印的属性，如颜色、大小等
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(50);

        // 计算水印数量以及水印之间的间隔
        int watermarkCount = 10;
        float watermarkInterval = diagonalLength / (watermarkCount + 1);

            // 在Canvas对象上绘制水印
        for (int i = 1; i <= watermarkCount; i++) {
            float x = watermarkInterval * i;
            float y = x;
            canvas2.save();
            canvas2.rotate(diagonalAngle, diagonalLength / 2, diagonalLength / 2);
            canvas2.drawText(watermark, x, y, paint);
            canvas2.restore();
        }

        // 将带有水印的Bitmap对象设置为结果result
        result = bitmapWithWatermark;
        return result;
    }


    //软件目录存图片  返回存储路径
    public static String saveBitmapToInternalStorage(Context context, String fileName, Bitmap bitmap) {
        FileOutputStream fos = null;
        String filePath = null;
        try {
            //创建文件夹
            File directory = context.getDir("pictures", Context.MODE_PRIVATE);
            //创建文件
            File file = new File(directory, fileName);
            fos = new FileOutputStream(file);
            //写入文件
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            filePath = file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }

    //从内部存储读取存贮的图片，返回Bitmap
    public static Bitmap loadBitmapFromInternalStorage(Context context, String fileName) {
        Bitmap bitmap = null;
        try {
            // 获取文件路径
            File directory = context.getDir("pictures", Context.MODE_PRIVATE);
            File file = new File(directory, fileName);
            // 从文件中读取 Bitmap 对象
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    //根据路径删除图片
    public static boolean deleteFileFromInternalStorage(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }



    //将图片生成PdfDocument
    public static PdfDocument creat_pdf(Bitmap image){
        // 计算A4纸的尺寸和图片的尺寸
        float pageWidth = 8.27f * 72; // A4纸的宽度（8.27英寸），转换为像素（1英寸=72像素）
        float pageHeight = 11.69f * 72; // A4纸的高度（11.69英寸），转换为像素
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();

        // 创建一个PdfDocument对象
        PdfDocument pdfDocument = new PdfDocument();

        // 创建一个PdfDocument.PageInfo对象，指定页面的宽度、高度和页码等信息
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder((int)pageWidth, (int)pageHeight, 1).create();

        // 开始一个新的页面，并获取Canvas对象
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // 计算图片应该在页面上的位置
        float left = (pageWidth - imageWidth) / 2;
        float top = (pageHeight - imageHeight) / 2;

        // 在Canvas对象上绘制图片
        canvas.drawBitmap(image, left, top, null);

        // 结束页面，并将页面添加到PdfDocument对象中
        pdfDocument.finishPage(page);


        pdfDocument.close();

        return pdfDocument;
    }






}

