package com.jetpacker06.jha;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;
import net.hypixel.api.reply.PlayerReply;

import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
@SuppressWarnings("deprecation")
public class JHA {
    public static UUID myUUID = UUID.fromString("692c31c4-de79-418b-9f50-9e27ac540092");
    public static String key = System.getProperty("apiKey", "b97a8655-29a9-4d20-aa21-d4dbe418e143");
    public static HypixelAPI API = new HypixelAPI(new ApacheHttpClient(UUID.fromString(key)));
    public static void log(Object o) {
        System.out.println(o);
    }
    public static String stringInput(String prompt) {
        log(prompt);
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
    public static void main(String[] args) {
        String input = stringInput("Enter a username or UUID.");
        boolean isUUID = false;
        if (input.length() == 36) {
            log("UUID detected...");
            isUUID = true;
        } else {
            log("Username detected...");
        }
        PlayerReply apiReply = null;
        try {
            if (isUUID) {
                apiReply = API.getPlayerByUuid(UUID.fromString(input)).get();
            } else {
                apiReply = API.getPlayerByName(input).get();
            }
        } catch (ExecutionException e) {
            log("ExecutionException occurred");
        } catch (InterruptedException ignored) {
            log("InterruptedException occurred");
        } finally {
            API.shutdown();
        }
        if (apiReply == null) {
            log("Something went wrong");
            return;
        }
        PlayerReply.Player player = apiReply.getPlayer();
        if (!player.exists()) {
            log("Player does not exist!");
            return;
        }
        log("Here is " + player.getName() + "'s rank: " + player.getHighestRank());
    }
}