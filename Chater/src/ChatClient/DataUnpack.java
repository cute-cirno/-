package ChatClient;

public class DataUnpack {                                       //消息拆包
    String data;
    String type;
    String sender;
    String receiver;
    String[] dataList;
    String[] userList;

    DataUnpack(String data){
        this.data = data;
        this.dataList = data.split("@");
        this.type = dataList[0];
        this.sender = dataList[1];
        this.receiver = dataList[2];
    }
    public String getType(){
        return dataList[0];
    }
    public String getSender(){
        return dataList[1];
    }
    public String getReceiver(){
        return dataList[2];
    }
    public String getContent(){
        return dataList[3];
    }
    public String[] getUserList(){
        String users = data.substring(data.indexOf("@")+data.split("@")[1].length()+data.split("@")[2].length()+3);
        userList = users.split("@");
        return userList;
    }
}
