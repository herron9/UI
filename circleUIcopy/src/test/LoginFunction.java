package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.CircleClient;



public class LoginFunction {

	public static String AccessToken;
	public static ArrayList<ChatHistory> receiver=new ArrayList<>();
	
	public static void History(String friendname,String content,String time,String sourceID) {
		boolean find=false;
		int index=0;
		for(int i=0;i<receiver.size();i++){
			if(friendname.equals(receiver.get(i).friendname)){
				find=true;
				index=i;
			}
		}
		if(find==true){
			chatRecord record = new chatRecord();
			record.message=content;
			record.time=time;
			record.sourceID=sourceID;
			receiver.get(index).history.add(record);
		}
		else{
			ChatHistory chat = new ChatHistory();
			chat.friendname=friendname;
			chatRecord record = new chatRecord();
			record.message=content;
			record.time=time;
			record.sourceID=sourceID;
			chat.history.add(record);
			receiver.add(chat);
		}
	}
	
	public static void RecallHistory(ChattingPanel CPanel,String friendname) {
		CPanel.ChatArea.setText(null);
		for(int i=0;i<receiver.size();i++){
			if(friendname.equals(receiver.get(i).friendname)){
				for(int j=0;j<receiver.get(i).history.size();j++){
					CPanel.ChatArea.append(receiver.get(i).history.get(j).sourceID);
					CPanel.ChatArea.append(" "+receiver.get(i).history.get(j).time+"\n");
					CPanel.ChatArea.append(receiver.get(i).history.get(j).message+"\n\n");
				}
			}
		}
	}
	
	public static void Login(String operation,String username,String password) {
		int p1,p2;
		String str1="circleAccessToken";
		String str2="code";
		
		String response = excutePost("http://ec2-54-86-38-175.compute-1.amazonaws.com:8080/CircleAuthenticationService/"+operation+"username="+username+"&password="+password,"");
		System.out.println(response);
		  if(response.indexOf("true")!=-1){
      		  JOptionPane.showMessageDialog(null,"login successful");
      		  operation="accessToken-Verification?";
			  p1=response.indexOf(str1);
			  p2=response.indexOf(str2);
			  AccessToken=response.substring(p1+20, p2-3);
      		  VerifyAccessToken(operation,AccessToken);
      		try {
				CircleClient client0 = new CircleClient(LoginPanel.circleAccessToken, new MsgReceiver());	
				ClientFunction.GetClient(client0);
      		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
      		  }
      		  operation="friendList-request?";
    		  GetFriendList(operation, AccessToken);
    		  operation="create-user-profile?";
    		  CreateProfile(operation, AccessToken);
          	  MainFrame.cl.show(MainFrame.panelCont, "Main");
		  }
		  else{
      		  JOptionPane.showMessageDialog(null,"login failed");
		  }
	}
	
	public static void VerifyAccessToken(String operation,String AccessToken) {
		int p1,p2;
		String str1="username";
		String str2="code";
		String response = excutePost("http://ec2-54-86-38-175.compute-1.amazonaws.com:8080/CircleAuthenticationService/"+operation+"accessToken="+AccessToken,"");
		System.out.println(response);
		  if(response.indexOf("true")!=-1){
			  p1=response.indexOf(str1);
			  p2=response.indexOf(str2);
			  LoginPanel.circleAccessToken=response.substring(p1+11, p2-3);
		  }
		  else{
      		  JOptionPane.showMessageDialog(null,"failed");
		  }
	}
	
	public static void GetFriendList(String operation,String AccessToken) {
		String response = excutePost("http://ec2-54-86-38-175.compute-1.amazonaws.com:8080/CircleAuthenticationService/"+operation+"accessToken="+AccessToken,"");
		System.out.println(response);
		LoginPanel.names=parseFriendList(response);
		MainLayout.FriendList.removeAll();
		MainLayout.FriendList=new FriendPanel(LoginPanel.names);
		MainLayout.MainUppage.add(MainLayout.FriendList,"FriendList");    
	}
	
	public static void AddAFriend(String operation,String AccessToken,String friendname,JPanel panel) {
		String response = excutePost("http://ec2-54-86-38-175.compute-1.amazonaws.com:8080/CircleAuthenticationService/"+operation+"accessToken="+AccessToken+"&friend="+friendname,"");
		System.out.println(response);
		if(response.indexOf("true")!=-1){
			operation="friendList-request?";
			String response1 = excutePost("http://ec2-54-86-38-175.compute-1.amazonaws.com:8080/CircleAuthenticationService/"+operation+"accessToken="+AccessToken,"");
			LoginPanel.names=parseFriendList(response1);
			MainLayout.FriendList.removeAll();
			MainLayout.FriendList=new FriendPanel(LoginPanel.names);
			MainLayout.MainUppage.add(MainLayout.FriendList,"FriendList");
        	MainLayout.MainpageCl.show(MainLayout.MainUppage, "FriendList");
		}
      	panel.updateUI();
	}
	
	public static void Test(String operation,String username,String password) {
		String response = excutePost("http://ec2-54-86-38-175.compute-1.amazonaws.com:8080/CircleAuthenticationService/"+operation+"username="+username+"&password="+password,"");
		System.out.println(response);
		  if(response.indexOf("true")!=-1){
      		  JOptionPane.showMessageDialog(null,"registration successful");
          	  MainFrame.cl.show(MainFrame.panelCont, "Log");
          	  
		  }
		  else{
      		  JOptionPane.showMessageDialog(null,"registration failed");

		  }
	}
	

	public static String excutePost(String targetURL, String urlParameters) {
		HttpURLConnection connection = null;
		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.close();

			// Get Response
			java.io.InputStream is =  connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if
															// not Java 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	public static String[] parseFriendList(String s) {
		String[] friendList;
		Pattern p = Pattern.compile("\"friendList\":\\[(.+)\\]");
		Matcher m = p.matcher(s);
		String friendNameList ="";
		if(m.find()) {
			friendNameList = m.group(1);
		}
		friendList = friendNameList.split(",");
		for (int i = 0; i < friendList.length; i++) {
			String name = friendList[i];
			if (name.length() > 0) {
				friendList[i] = (name.substring(1,  name.length() - 1));
			}
		}
		return friendList;
	}
	

	//--------------------------------ProfileFunction------------------------------------	
	public static void ModifyPassword(String operation,String AccessToken,String newpassword){
		String response = excutePost("http://ec2-54-86-38-175.compute-1.amazonaws.com:8080/CircleAuthenticationService/"+operation+"accessToken="+AccessToken+"&password="+newpassword,"");
		System.out.println(response);
		if(response.indexOf("true")!=-1){
			JOptionPane.showMessageDialog(null,"modification successful");
		}
		else{
			JOptionPane.showMessageDialog(null,"modification failed");
		}

	}
	
	public static void CreateProfile(String operation,String AccessToken) {
		String response = excutePost("http://ec2-54-86-38-175.compute-1.amazonaws.com:8080/CircleAuthenticationService/"+operation+"accessToken="+AccessToken,"");
		System.out.println(response);
	}
	
	public static void ModifyProfile(String operation,String AccessToken,String gender,String phonenumber) {
		String response = excutePost("http://ec2-54-86-38-175.compute-1.amazonaws.com:8080/CircleAuthenticationService/"+operation+"accessToken="+AccessToken+"&gender="+gender+"&phonenumber="+phonenumber,"");
		System.out.println(response);
	}
	
	//-----------------------------------------------------------------------------------

}
