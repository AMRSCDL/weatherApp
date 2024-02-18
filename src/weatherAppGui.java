import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class weatherAppGui extends JFrame {
    private JSONObject weatherData;

    public weatherAppGui(){
        super("Hava Durumu Uygulaması");

        // program kapatıldıktan sonra işlem sonlanır
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ekran boyut ayarları
        setSize(450, 650);

        // ekranın ortasında başlatıyoruz
        setLocationRelativeTo(null);

        setLayout(null);

        // resize (boyutunun değiştirlimesi) edilmesini engelliyoruz
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents(){
        // arama alanı
        JTextField searchTextField = new JTextField();

        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        // hava durumu resmi
        JLabel weatherConditionImage = new JLabel(loadImage("src/weatherPics/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        // derece metni
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));

        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // hava durumu açıklaması
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        // nem görsel
        JLabel humidityImage = new JLabel(loadImage("src/weatherPics/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        // nem metin
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        // rüzgar hıı görsel
        JLabel windspeedImage = new JLabel(loadImage("src/weatherPics/wind.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);

        // rüzgar hızı metin
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);

        // arama butonu
        JButton searchButton = new JButton(loadImage("src/weatherPics/search.png"));

        // arama kutusunun üzerine gelince imlecin şekli değişsin
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();

                // metin alanı boşlukları kaldırma
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                // hava durmu verilerini al
                weatherData = WeatherApp.getWeatherData(userInput);


                // hava durmu resmi güncelle
                String weatherCondition = (String) weatherData.get("weather_condition");

                // duruma bağlı karşılık gelen hava durmuu resmini güncelle
                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/weatherPics/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/weatherPics/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/weatherPics/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/weatherPics/snow.png"));
                        break;
                }

                // sıcaklık metni güncelleme
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");

                // hava durmu metini güncelleme
                weatherConditionDesc.setText(weatherCondition);

                // nem metini güncelleme
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // rüzgar hızı metin güncelleme
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
            }
        });
        add(searchButton);
    }

    private ImageIcon loadImage(String resourcePath){
        try{
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("kaynak bulunamadı");
        return null;
    }
}


