import javax.naming.Name;
import javax.xml.stream.events.Comment;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.Comparator;
public class socialMediaPlatform {
    public static void main(String[] args){

        User user1 = new User("Sena");
        User user2 = new User("Hatice");
        User user3 = new User("Taha");

        user1.follow(user2);
        user1.follow(user3);
        user2.follow(user1);
        user2.follow(user3);
        user3.follow(user1);
        user3.follow(user2);
        user2.createPost("Herkese merhaba");
        user3.addToPostFavorites(user2,0);
        user1.addCommentToPost(user2, 0, "Merhaba, harika post!");
        user1.addToPostFavorites(user2, 0);
        user3.addCommentToPost(user2, 0, "Merhaba");
        user1.showFeed();

    }

    // Comment sınıfı
    static class Comment {
        private User user;
        private String text;

        public Comment(User user, String text) {
            this.user = user;
            this.text = text;
        }

        public User getUser() {

            return user;
        }

        public String getText() {

            return text;
        }
    }

    // Post sınıfı
    static class Post {
        private int id;
        private String content;

        //begeni sayısı ve yorum sayılarını tutabilirn.
        private LinkedHashMap<Integer, Comment> comments; // Post ile ilgili yorumlar

        public Post(int id, User user, String content) {
            this.id = id;
            this.content = content;
            this.comments = new LinkedHashMap<>();
        }

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public void addComment(Comment comment) {
            comments.put(comments.size(), comment);
        }

        public void showComments() {
            for (Comment comment : comments.values()) {
                System.out.println( " Yorumcu: " + comment.getUser().getName() + "  -  Yorum: " + comment.getText());
            }
        }
    }

    // User sınıfı
    static class User {
        private String name;
        private LinkedHashMap<Integer, Post> posts; // Kullanıcının gönderileri
        private HashSet<User> following; // Takip edilen kullanıcılar
        private TreeSet<Post> favorites; // Beğenilen gönderiler
        private static int postCounter = 0; // Gönderi sayacı

        public User(String name) {
            this.name = name;
            this.posts = new LinkedHashMap<>();
            this.following = new HashSet<>();
            this.favorites = new TreeSet<>(Comparator.comparing(Post::getId)); // Post ID'sine göre sıralama yapıyoruz
        }

        public String getName() {
            return name;
        }

        public void follow(User user) {
            following.add(user);
            System.out.println(name + ", " + user.getName() + " kullanıcısını takip ediyor.");
        }

        public void createPost(String content) {
            Post newPost = new Post(postCounter++, this, content);
            posts.put(newPost.getId(), newPost);
            System.out.println(name + " yeni bir gönderi yayınladı: " + content);
        }

        public void addCommentToPost(User user, int postId, String comment) {
            Post post = user.getPost(postId);
            if (post != null) {
                post.addComment(new Comment(this, comment));
                System.out.println(name + ", " + user.getName() + "'in gönderisine yorum yaptı: " + comment);
            }
        }

        public void addToPostFavorites(User user, int postId) {
            Post post = user.getPost(postId);
            if (post != null) {
                favorites.add(post);
                System.out.println(name + ", " + user.getName() + "'in gönderisini beğendi: " + post.getContent());
            }
        }

        public Post getPost(int postId) {
            return posts.get(postId);
        }

        public void showFeed() {
            System.out.println("\n" + name + "'in Ana Sayfası:");
            for (User user : following) {
                user.showPosts();
            }
        }

        public void showPosts() {
            for (Post post : posts.values()) {
                System.out.println(name + "'in gönderisi: " + post.getContent());
                post.showComments();
            }
        }
    }
}