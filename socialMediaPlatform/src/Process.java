import java.util.*;

public class Process {

    private User currentUser;
    private List<User> otherUsers;
    private  Logger logger;

    public Process() {
       this.logger = new Logger();
        this.currentUser = new User("Sena");

        User user1 = new User("Hatice");
        User user2 = new User("Ayşe");
        User user3 = new User("Taha");
        User user4 = new User("Ali");

        this.otherUsers = Arrays.asList(user1, user2, user3, user4);

        currentUser.follow(user1);
        logger.log("Sena " + user1.getName() + " Kullanıcısını takip ediyor.");

    }


    // Ana sayfayı gösteren metod
    public void showHomePage() {
        currentUser.showFeed();
        logger.printLogs();
    }

    public void createPost(String content) {
        currentUser.createPost(content);
        logger.log(currentUser.getName() + " bir gönderi oluşturdu: " + content);

    }

    public void followUser(String userNameToFollow) {
        for (User user : otherUsers) {
            if (user.getName().equals(userNameToFollow)) {
                if(currentUser.nowFollow(user)){
                    logger.log(currentUser.getName() + ", " + user.getName() + "  kullanıcısını zaten takip ediyor.");
                } else {
                    currentUser.follow(user);
                    logger.log(currentUser.getName() + " " + user.getName() + " kullanıcısını takip etti.");
                }
                return;
            }
        }
        System.out.println("Kullanıcı bulunamadı.");
        logger.log(currentUser.getName() + " " + userNameToFollow +" Kullanıcısını uygulamada bulamadı");

    }

    public void addCommentToPost(String userNameForComment, int postIdForComment, String comment) {

        for (User user : otherUsers) {
            if (user.getName().equals(userNameForComment)) {
                currentUser.addCommentToPost(user, postIdForComment, comment);
                logger.log(currentUser.getName() + " " + user.getName() + " kullanıcısının gönderisine yorum yaptı: " + comment);
                return;
            }
        }
        System.out.println("Kullanıcı bulunamadı.");
    }


    public void addToPostFavorites(String userNameForLike, int postIdForLike) {
        for (User user : otherUsers) {
            if (user.getName().equals(userNameForLike)) {
                currentUser.addToPostFavorites(user, postIdForLike);
                logger.log(currentUser.getName() + " " + user.getName() + " kullanıcısının gönderisini favorilere ekledi.");
                return;
            }
        }
        System.out.println("Kullanıcı bulunamadı.");
    }
    public void showFavorites() {

        logger.log(currentUser.getName() + " favorilerini görüntüledi.");

       /* if (favorites.isEmpty()) {
            System.out.println(name + " kullanıcısının favori gönderisi yok.");
        } else {
            System.out.println(name + " kullanıcısının favori gönderileri:");
            for (Post post : favorites) {
                System.out.println("Gönderi ID: " + post.getId() + " - İçerik: " + post.getContent());
            }
        } */
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
        public boolean nowFollow(User user) {
            return following.contains(user);
        }

        public void createPost(String content) {
            Post newPost = new Post(postCounter++, this, content);
            posts.put(newPost.getId(), newPost);
            System.out.println(name + " yeni bir gönderi yayınladı: " + content);
        }

        public void addCommentToPost(User user, int postId, String comment) {
            if (following.contains(user)) {
            Post post = user.getPost(postId);

                post.addComment(new Comment(this, comment));
                System.out.println(name + ", " + user.getName() + "'in gönderisine yorum yaptı: " + comment);

            }
        }

        public void addToPostFavorites(User user, int postId) {
                if (following.contains(user)) {
                    Post post = user.getPost(postId);
                    if (post != null) {
                        favorites.add(post);
                        System.out.println(name + ", " + user.getName() + "'in gönderisini beğendi: " + post.getContent());
                    }
                } else {
                    System.out.println("Beğenebilmek için " + user.getName() + " adlı kullanıcıyı takip etmelisiniz.");
                }
            }

        public Post getPost(int postId) {
            return posts.get(postId);
        }

        public void showFeed() {
           // System.out.println("\n" + name + "'in Ana Sayfası:");
            Set<Post> feed = new HashSet<>();
            for (User user : following) {
                feed.addAll(user.posts.values());
            }
            feed.addAll(favorites);

            for (Post post : feed) {
                System.out.println(post.getUser().getName() + "'in gönderisi: " + post.getContent());
                post.showComments();
            }
        }

        public void showPosts() {
            for (Post post : posts.values()) {
                System.out.println(name + "'in gönderisi: " + post.getContent());
                post.showComments();
            }
        }
    }
    //*******************************
    static class Post{

        private int id;
        private String content;
        private User user;
        private LinkedHashMap<Integer, Comment> comments; // Post ile ilgili yorumlar

        public Post(int id, User user, String content) {
            this.id = id;
            this.user = user;
            this.content = content;
            this.comments = new LinkedHashMap<>();
        }

        public User getUser(){
            return user;
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
                System.out.println("Yorum: " + comment.getText() + " - Yorumcu: " + comment.getUser().getName());
            }
        }
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
    static class Logger {
        private List<String> logs;

        public Logger() {
            this.logs = new ArrayList<>();
        }

        public void log(String message) {
            logs.add(new Date() + " - " + message);
        }

        public void printLogs() {
            System.out.println("...");
            for (String log : logs) {
                System.out.println(log);
            }
        }
    }
}
