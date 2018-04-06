import cli.CLI;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime()
                .addShutdownHook (
                        new Thread (CLI::start)
                ) ;
    }
}
