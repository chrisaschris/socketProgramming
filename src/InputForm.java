import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputForm extends JFrame {
    private JTextField senderEmailField;
    private JTextField receiverEmailField;
    private JTextField subjectField;
    private JTextArea contentArea;
    public InputForm() {
        setTitle("Email Input Form");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Insets 설정 (여백 추가)
        Insets insets = new Insets(10, 10, 10, 10); // 위, 왼쪽, 아래, 오른쪽 여백
        gbc.insets = insets;

        // 보내는 이메일 주소
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3; // 레이블의 너비 비율
        add(new JLabel("보내는 이메일 주소:"), gbc);

        senderEmailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1; // 텍스트 필드의 너비 비율
        add(senderEmailField, gbc);

        // 받는 이메일 주소
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // 레이블의 너비 비율
        add(new JLabel("받는 이메일 주소:"), gbc);

        receiverEmailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1; // 텍스트 필드의 너비 비율
        add(receiverEmailField, gbc);

        // 메일 제목
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3; // 레이블의 너비 비율
        add(new JLabel("메일 제목:"), gbc);

        subjectField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1; // 텍스트 필드의 너비 비율
        add(subjectField, gbc);

        // 메일 내용
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3; // 레이블의 너비 비율
        add(new JLabel("메일 내용:"), gbc);

        contentArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        gbc.gridx = 1;
        gbc.gridheight = 3; // 메일 내용 영역이 3행 차지
        gbc.weighty = 0.8; // 높이 비율
        add(scrollPane, gbc);

        // 보내기 버튼
        JButton sendButton = new JButton("보내기");
        sendButton.setPreferredSize(new Dimension(100, 40)); // 버튼 크기 조정
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String senderEmail = senderEmailField.getText();
                String receiverEmail = receiverEmailField.getText();
                String subject = subjectField.getText();
                String content = contentArea.getText();

                // 메인 클래스에 전달
                Main.processEmail(senderEmail, receiverEmail, subject, content);
            }
        });

        // 버튼 위치 조정
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // 오른쪽 아래로 위치 조정
        gbc.gridwidth = 2; // 버튼이 두 열 차지
        add(sendButton, gbc);
    }
}
