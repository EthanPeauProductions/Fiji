import ij.ImagePlus;
import ij.IJ;
import ij.plugin.ChannelSplitter;
import ij.plugin.RGBStackMerge;
import ij.CompositeImage;
import ij.gui.NewImage;

import java.io.File;
import java.util.HashMap;

public class RGBPhoto{
  private String imageDir = null;
  private String imageName = null;
  private String imagePath = null;
  private String imageExt = null;
  private File imageFile = null;

  private ImagePlus image = null;
  private ImagePlus redChannel = null;
  private ImagePlus greenChannel = null;
  private ImagePlus blueChannel = null;

  // Nice to have, but not required for basic functions
  private String camera = null;
  private String filter = null;

  /*
   * Create RGBPhoto from photo directory
   * @param: dir - Directory of photo
   * @return: none
   */
  public RGBPhoto(String dir, String fname, String path, String cmodel){
    imageDir = dir;
    imageName = fname;
    imagePath = path;
    imageExt = getExtension(imagePath);

    image = new ImagePlus(imagePath);

    // Get channels
    ImagePlus imp = image;
    if (imp.getNChannels() == 1) {
        imp = new CompositeImage(image);
    }
    ImagePlus[] imageBands = ChannelSplitter.split((ImagePlus)imp);
    redChannel = imageBands[0];
    blueChannel = imageBands[1];
    greenChannel = imageBands[2];

    camera = cmodel;

    if( camera == null ){
      // Usually happens when the correct valueMap is not supplied. AKA
      // qrFileDialogValues because the camera model is not set there.
      // This is ok for qr code because it is not needed.
    }

    /*
    if( camera != null ){
      if( camera.equals(CalibrationPrompt.SURVEY2_RED) ){
        filter = "650";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_GREEN) ){
        filter = "548";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_BLUE) ){
        filter = "450";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_NDVI) ){
        filter = "660/850";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_NIR) ){
        filter = "850";
      }else if( camera.equals(CalibrationPrompt.OTHER_CAMERA) ){
        //@TODO Support selection of filter
        //filter = valueMap.get(CalibrationPrompt.MAP_FILTER);
      }else{
        IJ.log("Camera not supported");
      }
    }*/
    setFilter(camera);

  }

  public RGBPhoto( ImagePlus image ){
    this.image = image;

    // Get channels
    ImagePlus imp = image;
    if (imp.getNChannels() == 1) {
        imp = new CompositeImage(image);
    }
    ImagePlus[] imageBands = ChannelSplitter.split((ImagePlus)imp);
    redChannel = imageBands[0];
    blueChannel = imageBands[1];
    greenChannel = imageBands[2];

  }

  public RGBPhoto( ImagePlus[] channels, String cam ){
    redChannel = channels[0];
    greenChannel = channels[1];
    blueChannel = channels[2];

    RGBStackMerge merger = new RGBStackMerge();
    image = merger.mergeChannels(channels, false);
    //image = new CompositeImage(image);

    camera = cam;
    setFilter(camera);
  }


  public RGBPhoto(HashMap<String, String> valueMap){
    imageDir = valueMap.get(CalibrationPrompt.MAP_IMAGEDIR);
    imageName = valueMap.get(CalibrationPrompt.MAP_IMAGEFILENAME);
    imagePath = valueMap.get(CalibrationPrompt.MAP_IMAGEPATH);
    imageExt = getExtension(imagePath);

    image = new ImagePlus(imagePath);

    // Get channels
    ImagePlus imp = image;
    if (imp.getNChannels() == 1) {
        imp = new CompositeImage(image);
    }
    ImagePlus[] imageBands = ChannelSplitter.split((ImagePlus)imp);
    redChannel = imageBands[0];
    blueChannel = imageBands[1];
    greenChannel = imageBands[2];

    camera = valueMap.get(CalibrationPrompt.MAP_CAMERA);

    if( camera == null ){
      // Usually happens when the correct valueMap is not supplied. AKA
      // qrFileDialogValues because the camera model is not set there.
      // This is ok for qr code because it is not needed.
    }
    /*
    if( camera != null ){
      if( camera.equals(CalibrationPrompt.SURVEY2_RED) ){
        filter = "650";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_GREEN) ){
        filter = "548";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_BLUE) ){
        filter = "450";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_NDVI) ){
        filter = "660/850";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_NIR) ){
        filter = "850";
      }else if( camera.equals(CalibrationPrompt.OTHER_CAMERA) ){
        filter = valueMap.get(CalibrationPrompt.MAP_FILTER);
      }else{
        IJ.log("Camera not supported");
      }
    }*/
    setFilter(camera);
  }

  public ImagePlus[] splitStack(){
    ImagePlus imp = image;
    if (imp.getNChannels() == 1) {
        imp = new CompositeImage(image);
    }
    ImagePlus[] imageBands = ChannelSplitter.split((ImagePlus)imp);
    /*
    redChannel = scaleImage(imageBands[0], "Red");
    greenChannel = scaleImage(imageBands[1], "Green");
    blueChannel = scaleImage(imageBands[2], "Blue");

    imageBands = new ImagePlus[]{redChannel, greenChannel, blueChannel};
    //ImagePlus visImage = scaleImage(imageBands[visBandIndex], "visImage");
    //ImagePlus nirImage = scaleImage(imageBands[nirBandIndex], "nirImage");
    */
    redChannel = imageBands[0];
    blueChannel = imageBands[1];
    greenChannel = imageBands[2];

    return imageBands;
  }

  public void setFilter( String camera ){
    if( camera != null ){
      if( camera.equals(CalibrationPrompt.SURVEY2_RED) ){
        this.filter = "650";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_GREEN) ){
        this.filter = "548";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_BLUE) ){
        this.filter = "450";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_NDVI) ){
        this.filter = "660/850";
      }else if( camera.equals(CalibrationPrompt.SURVEY2_NIR) ){
        this.filter = "850";
      }else if( camera.equals(CalibrationPrompt.OTHER_CAMERA) ){
        //this.filter = valueMap.get(CalibrationPrompt.MAP_FILTER);
        filter = null;
      }else{
        IJ.log("Camera not supported");
      }
    }
  }

  public String getExtension(String path){
    return "yo";
  }

  public String getExtension(){
    return "yo";
  }

  public ImagePlus getImage(){
    return image;
  }

  public String getCameraType(){
    return camera;
  }

  public String getFilterType(){
    return filter;
  }

  public ImagePlus getRedChannel(){
    return redChannel;
  }

  public ImagePlus getGreenChannel(){
    return greenChannel;
  }

  public ImagePlus getBlueChannel(){
    return blueChannel;
  }

  public void show(){
    image.show();
  }

}
