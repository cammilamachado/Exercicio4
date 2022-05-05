import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.*;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class SinteseFala {

    private static String YourSubscriptionKey = "10032001463DB159"; // My Key
    private static String YourServiceRegion = "Brasil"; // Região

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FalaConfig falaConfig = FalaConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);

        falaConfig.setSinteseFalaLanguage("fr-FR");
        falaConfig.setSinteseFalaVoiceName("fr-FR-DeniseNeural"); // Linguagem escolhida: Francês (voz feminina)

        SinteseFala sinteseFala = new SinteseFala(falaConfig);

        System.out.println("Digite algum texto que você deseja falar: \n");
        String text = new Scanner(System.in).nextLine();
        if (text.isEmpty()) {
            return;
        }

        SinteseFalaResult falaReconhecimentoResult = sinteseFala.SpeakTextAsync(text).get();

        if (falaReconhecimentoResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
            System.out.println("Fala sintetizada para alto-falante para texto [" + text + "]\n");
        } else if (falaReconhecimentoResult.getReason() == ResultReason.Canceled) {
            SinteseFalaCancellationDetails cancellation = SinteseFalaCancellationDetails
                    .fromResult(falaReconhecimentoResult);
            System.out.println("Canceled: Razão = " + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("Canceled: Erro de código = " + cancellation.getErrorCode());
                System.out.println("Canceled: Detalhes de erro = " + cancellation.getErrorDetails());
                System.out.println("Canceled: Você definiu a chave do recurso de fala e os valores da região?");
            }
        }

        System.exit(0);
    }
}