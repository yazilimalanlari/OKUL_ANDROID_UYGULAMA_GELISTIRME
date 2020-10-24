# Android Uygulama Geliştirme | Ödev 1

## Konu: Kendini Tanıtma Uygulaması

## Açıklama
Ad, soyad, yaş gibi bilgilerin yer alacağı tek sayfalık Android mobil uygulaması.

### Uygulamada bulunması gereken bilgiler

* Ad
* Soyad
* Yaş
* Lise Bilgisi
* Memleket
* İş Deneyimleri

### Yapılandırma, ayarlar ve yapılacaklar

1. Uygulama içerisinde yer alacak static string ifadelerin values/strings.xml dosyasında tanımlanması.
2. Uygulama içerisinde yer alacak renklerin values/colors.xml dosyasında tanımlanması.
3. Uygulamada kullanılacak yazı fontlarının içe aktarılması.
4. Dinamik id kullanımı için values/ids.xml dosyasının oluşturulması ve id'lerin tanımlanması.
5. values/styles.xml dosyasında Splash ekranı için style tanımlanması ve diğer yapılandırmalar.

### Algoritma

1. Uygulamanın varsayılan pencere arkaplanı belirlenen splash görseli olacak.
2. Uygulama ilk açıldığında onCreate metodu çalıştığında ilk olarak varsayılan tema'ya geçilecek(performans için)
3. Ekranda gözükmesi gereken bilgiler activity_main.xml'den ayarlanacak.
4. Kotlin tarafında
    1. SpringAnimation ile animasyonlu bir açılış ayarlanacak.
    2. ObjectAnimator ile "İş Deneyimlerim" butonu sürekli olarak aşşağı yukarı hareket edecek animasyon ayarlanacak.
5. "İş Deneyimlerim" alanı için sayfanın alt bölümüne bir buton eklenecek. Tıklandığında 7. adıma gidilecek.
6. Dinamik olarak ConstraintLayout eklenecek ve ekranın yüksekliği kadar translationY değeri eksilecek.
    1. ConstraintLayout içerisine kapat ikonu, başlık, açıklama eklenecek.
        1. Kapat ikonuna tıklandığında SpringAnimaiton ile TRANSLATION_Y değeri sayfanın yüksekliği kadar eksilecek.
7. SpringAnimation ile eklenen ConstraitLayout'un TRANSLATION_Y değeri 0f olarak ayarlanacak ve animasyon başlatılacak.



### Sonuç

Uygulamanın geliştirilmesi tamamlandı.

#### Klasörler
* project/ Uygulamanın kaynak kodları.
* screenshots/ Uygulamadan alınan ekran görüntüleri ve App.mp4, App.gif dosyaları.

![](./screenshots/App.gif)