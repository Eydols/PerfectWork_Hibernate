package controllers;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


@ManagedBean(eager = true)
@SessionScoped
public class ImageController implements Serializable {

    private final int IMAGE_MAX_SIZE = 2048000;
    private byte[] uploadedImage;
    private String nameImage;
    private FileOutputStream fout = null;

    @ManagedProperty(value = "#{manListController}")
    private ManListController manListController;

    public ImageController() {
    }

    public void deleteDefaultImage() { // удаляет предыдущее изображение из папки /photos
        String photo = manListController.getSelectedMan().getPhoto();
        String s = System.getProperty("user.dir");
        File file = new File(s.substring(0, s.length() - 49) + "Documents\\NetBeansProjects\\PerfectWork_Hibernate\\web\\resources\\" + photo);
        file.delete();
    }

    public void recordUploadedFile() throws FileNotFoundException, IOException { // сохраняет выбранное изображение в папку /photos
        String s = System.getProperty("user.dir");
        try {
            File dest = new File(s.substring(0, s.length() - 49) + "Documents\\NetBeansProjects\\PerfectWork_Hibernate\\web\\resources\\images\\workers\\photos\\" + nameImage);
            fout = new FileOutputStream(dest);
            fout.write(uploadedImage);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public StreamedContent getUploadedImage() { // возвращает загруженное изображение, приобразованное в тип DefaultStreamedContent
        return getStreamedContent(uploadedImage);
    }

    public void handleFileUpload(FileUploadEvent event) { // слушатель, который отрабатывает при нажатии на кнопку Загрузить
        uploadedImage = event.getFile().getContents().clone();
        nameImage = event.getFile().getFileName();
    }

    private DefaultStreamedContent getStreamedContent(byte[] image) { // преобразовывает изображение из типа byte[] в DefaultStreamedContent, который можно сразу использовать на странице
        if (image == null) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(image);
            return new DefaultStreamedContent(inputStream, "image/jpg");
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void clear() {
        uploadedImage = null;
        nameImage = null;
    }

//<editor-fold defaultstate="collapsed" desc="ActionListeners">
    public ActionListener saveListener() {
        return new ActionListener() {
            @Override
            public void processAction(ActionEvent event) {
                if (uploadedImage != null) {
                    deleteDefaultImage();
                    manListController.getSelectedMan().setPhoto("images/workers/photos/" + nameImage); //сюда нужно будет записать имя файла
                    manListController.getSelectedMan().setImageEdited(true); // пока у меня нет этой переменной
                    
                    try {
                        recordUploadedFile();
                    } catch (IOException ex) {
                        Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                clear();
            }
        };
    }
    
    public ActionListener clearListener() {
        return new ActionListener() {
            @Override
            public void processAction(ActionEvent event) {
                clear();
            }
        };
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="геттеры/сеттеры">
    public int getImageMazSize() {
        return IMAGE_MAX_SIZE;
    }
    
    public byte[] getUploadedImageBytes() {
        return uploadedImage;
    }
    
    public String getNameImage() {
        return nameImage;
    }
    
    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }
    
    public ManListController getManListController() {
        return manListController;
    }
    
    public void setManListController(ManListController manListController) {
        this.manListController = manListController;
    }
//</editor-fold>

}
