# Android Garsonlar İçin Restoran Sipariş Uygulaması <br>

Açıklamalar ve Detaylı Bilgi İçin -> https://blog.mustafaergec.com.tr/android-firebase-restoran-siparis-alma-uygulamasi.html<br>

Android Restoran Sipariş Alma Uygulaması kısaca belirli bir restoranda çalışan garsonların masalar için sipariş almak, düzenlemek için kullanabildikleri bir mobil uygulama. Silme işlemi şu anda uygulamada mevcut değil.<br>

# Android Restoran Sipariş Alma Uygulaması Genel Özellikler
Değiştirilebilir yemek listesi<br>
Sadece istenilen kullanıcıların giriş yapabilmesi<br>
Yemekler sipariş oluşturulmadan önce görülebilir<br>
Uygulama ilk Giriş ekranında kullanıcıyı Giriş Ekranı karşılamaktadır. Giriş ekranında kullanıcı yetkili kişi tarafından verilen kullanıcı adı ve şifre ile giriş yapabilmektedir. Her hangi bir istenmeyen ziyaretçi/kullanıcı olmaması için kayıt ekranı ve şifre değiştirme ekranı bulunmamaktadır. Zira bu seçenekler ile istenmeyen kişiler uygulamaya giriş yapabilmektedir. Giriş Ekranını aşağıdaki resimde görebilirsiniz.<br>

Sadece geliştirme aşamasında kolaylık sağlaması açısından giriş ekranında şifre otomatik olarak verilmiş durumda. <br>

Aşağıdaki galeride Giriş ekranı ve kullanıcı giriş yaptıktan sonra karşılama ekranı (Ana Menü) görülmektedir.<br>

Karşılama ekranı kısaca giriş yapan kullanıcı e-posta adresi ile kullanıcıyı karşılar ve Hoş Geldiniz. Mesajını verir. Giriş yapan garson bu menüden uygulamadan Çıkış İşlemi ve Sipariş Oluşturma işlemlerini yapabilmektedir. Çıkış Yap butonuna basıldığında bir önceki menü olan Giriş Ekranı gelecektir. Daha sonra tekrar kullanıcı adı ve şifresi ile giriş yapması gerekmektedir.<br>

# Android Restoran Sipariş Alma Uygulaması Firebase İşlemleri

Uygulamada sipariş oluşturma, yemek listeleme işlemleri için Firebase kullanılmıştır. Firebase uygulaması şu an için çalışır durumda değildir. Denemeden önce Firebase üzerinden uygulama oluşturmalı ve gerekli ayarlamaları yapmalısınız.<br>
