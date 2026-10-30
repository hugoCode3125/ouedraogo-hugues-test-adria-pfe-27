package ma.enset.chatbotservice.controller;

import ma.enset.chatbotservice.agent.BotAgent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {

    private BotAgent agent;

    public ChatbotController(BotAgent agent) {
        this.agent = agent;
    }

    @GetMapping("/chat")
    public  String Chat(@RequestParam(name = "query", defaultValue = "Bonjour") String question,
                        @RequestParam(name = "conversationId",defaultValue = "user-1") String conversationId){
        return agent.Chat(question,conversationId);
    }
}
