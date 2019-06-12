package application;
import model.CEntityForm;

public class CMain {

  public CMain() {
  }
  public static void main(String[] args) {
	  
	 double d = 2.1;
	 Double p = 2.3;
	 System.out.println(d+", "+p);
	 
	 //System.out.println(new java.io.File("").getAbsolutePath()+"\\ProcessedSample2.bmp");
    CEntityForm classFormMain = new CEntityForm();
    //classFormMain.setSize(800,800);
    classFormMain.setVisible(true);

  }
}