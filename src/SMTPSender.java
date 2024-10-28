// SMTPServer.java
import java.net.*;
import java.io.*;

public class SMTPSender {
    public static void sendMail(
            String smtpServer, String sender, String recipient, String content)
            throws Exception {

        // STMP서버에 소켓 연결
        Socket socket = new Socket(smtpServer, 25);

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("서버에 연결되었습니다.");

        String line = inFromServer.readLine();
        System.out.println("응답:" + line);
        if(!line.startsWith("220")) {
            //예외처리
            throw new Exception("SMTP 서버가 아닙니다");
        }

        System.out.println("HELO 명령을 전송합니다.");
        outToServer.print("HELO test-client.com" + '\n');
        outToServer.flush();
        String lineFromServer = inFromServer.readLine();
        System.out.println("응답:" + lineFromServer);
        if(!lineFromServer.startsWith("250")) {
            throw new Exception("HELO 실패했습니다:" + lineFromServer);
        }

        System.out.println("RCPT 명령을 전송합니다.");
        outToServer.print("RCPT TO" + recipient + '\n');
        line = inFromServer.readLine();
        System.out.println("응답:" + line);
        if(!line.startsWith("250")) {
            throw new Exception("RCPT TO 에서 실패했습니다:" + line);
        }

        System.out.println("DATA 명령을 전송합니다.");
        outToServer.println("DATA");
        line = inFromServer.readLine();
        System.out.println("응답:" + line);
        if(!line.startsWith("354")) {
            throw new Exception("DATA 에서 실패했습니다:" + line);
        }

        System.out.println("본문을 전송합니다.");
        outToServer.println("content");
        outToServer.println(".");
        line=inFromServer.readLine();
        System.out.println("응답:" + line);
        if(!line.startsWith("250")) {
            throw new Exception("내용전송에서 실패했습니다:" + line);
        }

        System.out.println("접속 종료합니다.");
        outToServer.println("quit");

        outToServer.close();
        inFromServer.close();
        socket.close();
    }

    public static void main(String args[]) {
        try {
            SMTPSender.sendMail(
                    "smtp.naver.com",
                    "testoof@outlook.com",
                    "testoof@outlook.com",
                    "hello world"
            );
            System.out.println("==========================");
            System.out.println("메일이 전송되었습니다.");
        } catch(Exception e) {
            System.out.println("==========================");
            System.out.println("메일이 발송되지 않았습니다.");
            System.out.println(e.toString());
        }
    }
}
