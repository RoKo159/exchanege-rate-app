package pl.kurs.exchangerateapponspring.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pl.kurs.exchangerateapponspring.exceptions.InvalidInputDataException;
import pl.kurs.exchangerateapponspring.exceptions.NoConnectionException;
import pl.kurs.exchangerateapponspring.services.ICurrencyService;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

@ComponentScan(basePackages = "pl.kurs")
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);

        ICurrencyService currencyService = ctx.getBean(ICurrencyService.class);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Witaj w kantorze!");
        printOptions();
        int option = -1;

        do {
            try {
                switch ((option = scanner.nextInt())) {
                    case 1:
                        scanner.nextLine();
                        System.out.println("Jaką walutę chciałbyś wymienić?:");
                        String from = scanner.nextLine().toUpperCase();
                        System.out.println("W jakiej ilości?:");
                        BigDecimal amount = BigDecimal.valueOf(scanner.nextDouble());
                        scanner.nextLine();
                        System.out.println("Na jaką inną walutę?:");
                        String to = scanner.nextLine().toUpperCase();
                        BigDecimal exchange = currencyService
                                .exchange(from, to, amount);
                        System.out.println("Otrzymasz " + exchange + " " + to);
                        printOptions();
                        break;
                    case 0:
                        System.out.println("Koniec");
                        break;
                    default:
                        System.err.println("Nie rozpoznano opcji!");
                        printOptions();
                }
            } catch (InvalidInputDataException | NoConnectionException e) {//dodać NoConnectionException
                handleException(e);
            } catch (InputMismatchException e) {
                handleException(e);
                scanner.nextLine();
            }
        } while (option != 0);
        scanner.close();
        ctx.close();
    }

    private static void printOptions() {
        System.out.println("Wybierz jedną z opcji:");
        System.out.println("1 - wymiana gotówki");
        System.out.println("0 - wyjście");
    }

    private static void handleException(Throwable e) {
        if (e.getMessage() != null) System.err.println(e.getMessage());
        else System.err.println("Błąd wprowadzania!");
        printOptions();
    }
}