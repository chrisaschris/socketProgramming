import javax.swing.*;

public class Main {
    public static void processEmail(String senderEmail, String receiverEmail, String subject, String content) {
        // 이메일 처리 로직을 여기에 추가
        System.out.println("보내는 이메일: " + senderEmail);
        System.out.println("받는 이메일: " + receiverEmail);
        System.out.println("메일 제목: " + subject);
        System.out.println("메일 내용: " + content);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InputForm form = new InputForm();
            form.setVisible(true);
        });
    }
}