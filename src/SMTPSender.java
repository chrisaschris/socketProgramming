// SMTPServer.java
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.*;
import java.io.*;

public class SMTPSender {
    public static void sendMail(
            String smtpServer, String sender, String recipient, String content)
            throws Exception {

        // STMP서버에 소켓 연결
        Socket socket = new Socket(smtpServer, 587); // 소켓 생성

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 서버로부터 데이터 받는 버퍼 생성
        PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true); // 클라이언트에서 서버로 데이터 전송하는 버퍼 생성
        System.out.println("서버에 연결되었습니다.");

        String line = inFromServer.readLine();
        System.out.println("응답:" + line);
        if(!line.startsWith("220")) {
            //예외처리
            throw new Exception("SMTP 서버가 아닙니다");
        }

        System.out.println("HELO 명령을 전송합니다.");
        outToServer.print("HELO test-client.com" + "\r\n");
        outToServer.flush();
        String lineFromServer = inFromServer.readLine();
        System.out.println("응답:" + lineFromServer);
        if(!lineFromServer.startsWith("250")) {
            throw new Exception("HELO 실패했습니다:" + lineFromServer);
        }

        System.out.println("STARTTLS 명령을 전송합니다.");
        outToServer.print("STARTTLS" + "\r\n");
        outToServer.flush();
        String lineFromServer_2 = inFromServer.readLine();
        System.out.println("응답:" + lineFromServer_2);
        if(!lineFromServer_2.startsWith("220")) {
            throw new Exception("STARTTLS 실패했습니다:" + lineFromServer_2);
        }

        // TLS로 업그레이드
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(socket, smtpServer, 587, true);

        System.out.println("MAIL FROM 명령을 전송합니다.");
        outToServer.print("MAIL FROM: <" + sender + ">\r\n");
        outToServer.flush();
        String lineFromServer_3 = inFromServer.readLine();
        System.out.println("응답:" + lineFromServer_3);
        if(!lineFromServer_3.startsWith("250")) {
            throw new Exception("MAIL FROM 실패했습니다:" + lineFromServer_3);
        }

        System.out.println("RCPT 명령을 전송합니다.");
        outToServer.print("RCPT TO" + recipient + '\n');
        outToServer.flush();
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
                    "audtn0099@naver.com",
                    "sejunkwon@outlook.com",
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
