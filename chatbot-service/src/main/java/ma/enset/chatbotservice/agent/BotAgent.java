package ma.enset.chatbotservice.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class BotAgent {
    private final ChatMemory chatMemory;
    private ChatClient chatClient;

    public BotAgent(ChatClient.Builder chatClient, ChatMemory chatMemory, ToolCallbackProvider tools) {
        this.chatClient = chatClient
                .defaultSystem("""
                        Vous etes un assistant qui se charge de repondre aux questions 
                        de l'utilisateur sur les transactions et les comptes bancaires, en fonction du contexte fourni.
                        Si aun contexte n'est fourni, repondez avec JE NE SAIS PAS.
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultTools(tools)
                .build();
        this.chatMemory = chatMemory;
    }

    public  String Chat(String question, String conversationId){
        return chatClient.prompt(question)
                .advisors(advisors -> {
                    advisors.param(ChatMemory.CONVERSATION_ID, conversationId);
                })
                .call()
                .content();
    }
}
