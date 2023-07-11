package utils;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

public class SMSSender {
    public static void sendSMS(String phone, String content) {
        VonageClient client = VonageClient.builder()
                .apiKey(Constant.API_KEY_VONAGE)
                .apiSecret(Constant.API_SECRET_KEY_VONAGE)
                .build();

        TextMessage message = new TextMessage("Vonage APIs", phone, content);
        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
        }
    }

    public static void main(String[] args) {
        sendSMS("84769973715", "987654123");
    }
}
