import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Process process = new Process();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n" + "==== HOSGELDİNİZ ====");
            System.out.println("Ana Sayfa için 1");
            System.out.println("Gönderi oluşturmak için 2");
            System.out.println("Başka bir kullanıcıyı takip etmek için 3");
            System.out.println("Gönderiye yorum yapmak için 4 ");
            System.out.println("Gönderiyi beğenmek için 5");
            System.out.println("Favori gönderileri görmek için 6");
            System.out.println(" Uygulamadan Çıkmak için 7");
            System.out.print("Seçiminizi yapın: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: // Ana Sayfa
                    System.out.println("Ana Sayfa");
                    process.showHomePage();
                    // Seçenek 1'e ait işlemler buraya yazılır
                    break;
                case 2: // Gönderi Oluşturma
                    System.out.println("Gönderi Oluştur ");
                    String content = scanner.nextLine();
                    process.createPost(content);
                    break;
                case 3: //takip
                    System.out.println("Takip etmek istediğiniz kullanıcıyı seçiniz. ");
                    String userNameToFollow = scanner.nextLine();
                    process.followUser(userNameToFollow);
                    break;
                case 4: // Gönderiye yorum yapma
                    System.out.print("Yorum yapmak istediğiniz kullanıcının adını girin: ");
                    String userNameForComment = scanner.nextLine();
                    System.out.print("Yorum yapmak istediğiniz gönderi ID'sini girin: ");
                    int postIdForComment = scanner.nextInt();
                    scanner.nextLine(); // Buffer'ı temizlemek için
                    System.out.print("Yorumunuzu girin: ");
                    String comment = scanner.nextLine();
                    process.addCommentToPost(userNameForComment, postIdForComment, comment);

                    break;
                case 5: // gönderiyi begenmek için
                    System.out.print("Beğenmek istediğiniz kullanıcının adını girin: ");
                    String userNameForLike = scanner.nextLine();
                    System.out.print("Beğenmek istediğiniz gönderi ID'sini girin: ");
                    int postIdForLike = scanner.nextInt();
                    scanner.nextLine(); // Buffer'ı temizlemek için
                    process.addToPostFavorites(userNameForLike, postIdForLike);

                    break;

                case 6: //favori gönderiler
                    System.out.println("Favori gönderiler: ");
                    process.showFavorites();
                    break;

                case 7: // Çıkış
                    System.out.println("Uygulama Kapatıldı.");
                    exit = true;
                    break;
                default:
                    System.out.println("Geçersiz bir seçim yaptınız. Lütfen tekrar deneyin.");
                    break;
            }
        }

        scanner.close();
    }
}