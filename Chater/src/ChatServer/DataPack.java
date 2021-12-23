package ChatServer;

import java.util.List;

public class DataPack {                                                             //服务端消息包装
    String type;String sender;String receiver;String[] userList;String content;
    DataPack(String type, String sender, String receiver, String content){
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
    DataPack(String type, String sender, String receiver, String[] userList){
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.userList = userList;
    }
    public static String toDataPack(String type, String sender, String receiver, List<String> userList){ //"type@sender@receiver@content"
        StringBuilder sb = new StringBuilder(type+"@"+sender+"@"+receiver);
        for(String o:userList){
            sb.append("@").append(o);
        }
        return sb.toString();
    }
    public static String toDataPack(String type,String sender,String receiver,String content){ //"type@sender@receiver@content"
        return type + "@" + sender + "@" + receiver + "@" + content;
    }
}
