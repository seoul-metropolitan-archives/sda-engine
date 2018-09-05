package rmsoft.ams.seoul.ftp;

import com.jcraft.jsch.*;

public class FtpTest {
    public static void main(String args[]){

        String ftpHost = "115.84.166.51";
        int ftpPort = 22;
        String ftpUser = "sda-engine";
        String ftpPassword = "!@rldhrdnjs18!*";

        ChannelSftp sftpChannel;
        Session session;
        Channel channel;

        System.out.println("SFTP Connecting");

        try {
            // 1. JSch 객체를 생성한다.
            JSch jsch = new JSch();
            // 2. 세션 객체를 생성한다(사용자 이름, 접속할 호스트, 포트를 인자로 전달한다.)
            session = jsch.getSession(ftpUser, ftpHost, ftpPort);
            // 4. 세션과 관련된 정보를 설정한다.
            session.setConfig("StrictHostKeyChecking", "no");
            // 4. 패스워드를 설정한다.
            session.setPassword(ftpPassword);
            // 5. 접속한다.
            session.connect();

            // 6. sftp 채널을 연다.
            channel = session.openChannel("sftp");
            // 7. 채널에 연결한다.
            channel.connect();
            // 8. 채널을 FTP용 채널 객체로 캐스팅한다.
            sftpChannel = (ChannelSftp) channel;

            System.out.println("Connecting Completed!!");

        } catch (JSchException e) {
           e.printStackTrace();
        }
    }
}
