/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convert;

import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.media.jai.JAI;

/**
 *
 * @author Freddy
 */
public class imageconvert {
    public static boolean convertFormat(String inputImagePath,String outputImagePath, String formatName) throws IOException {
                boolean data=false;
                if("jpg".equals(formatName))
                {
                    OutputStream outputStream;
                    ImageWriter imageWriter;
                    ImageOutputStream imageOutputStream;
                    try (InputStream inputStream = new FileInputStream(inputImagePath)) {
                        outputStream = new FileOutputStream(outputImagePath+"."+formatName);
                        BufferedImage bufferedImage = ImageIO.read(inputStream);
                        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName(formatName);
                        if (!imageWriters.hasNext())
                            throw new IllegalStateException("Writers failed!!");
                        imageWriter = (ImageWriter) imageWriters.next();
                        imageOutputStream = ImageIO.createImageOutputStream(outputStream);
                        imageWriter.setOutput(imageOutputStream);
                        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
                        //compress quality and mode
                        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                        imageWriteParam.setCompressionQuality(0.7f);
                        //new image
                        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
                        data=true;
                        
		// close all stream data;
                        }
		outputStream.close();
		imageOutputStream.close();
		imageWriter.dispose();   
            }
                else if("gif".contentEquals(formatName))
                {
                    try (FileInputStream inputStream = new FileInputStream(inputImagePath)) {
                        FileOutputStream gifoutputstream = new FileOutputStream(outputImagePath+"."+formatName);
                        BufferedImage inputImage = ImageIO.read(inputStream);
                        data=ImageIO.write(inputImage, "GIF", gifoutputstream);
                        gifoutputstream.close();
                        inputStream.close();
                        data=true;
                    }
                }
                else if("png".contentEquals(formatName))
                {
                    RenderedOp src = JAI.create("fileload", inputImagePath);
                       FileOutputStream output = new FileOutputStream(outputImagePath+"."+formatName);
                       JAI.create("encode", src, output, "PNG",null);
                       JAI.create("filestore", src,outputImagePath+".png","PNG",null);
                       data= true;
                }
                else if ("tif".contentEquals(formatName))
                { 
                       RenderedOp src = JAI.create("fileload", inputImagePath);
                       FileOutputStream output = new FileOutputStream(outputImagePath+"."+formatName);
                       JAI.create("encode", src, output, "TIFF",null);
                       JAI.create("filestore", src,outputImagePath+".tif","TIFF",null);
                       data= true;
                }
                return data;
    }
}
