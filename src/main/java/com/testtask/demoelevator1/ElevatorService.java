package com.testtask.demoelevator1;

import org.springframework.stereotype.Service;

import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ElevatorService {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
    DelayUtil d = new DelayUtil();
    private int current = 7;


    synchronized public void down(int n) {
        while (current > n) {

            d.delay(10000);
            current--;
            if (queue.contains(current)) {
                queue.remove(current);
            }

        }
    }

    synchronized public void up(int n) {
        while (current < n) {
            d.delay(10000);
            current++;
            if (queue.contains(current)) {
                queue.remove(current);
            }
        }
    }

    public void print() {
        System.out.println("current flor is " + current);
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        System.out.println("Лифт в работае, для того чтобы поехать на нужный этаж - введите номер этажа \n чтобы вывести текущий этаж - введите 0 ");
        System.out.print("Сейчас лифт на 7 этаже.Введите этаж: ");
        String str;
        int flor;
        AtomicInteger move = new AtomicInteger();


        while (in.hasNextLine()) {
            str = in.nextLine();
            if (str.equals("0")) print();
            else if (str.equals("stop") || str.equals("start"))d.delay(2000);
            else {
                try {
                    flor = Integer.parseInt(str);
                    if (flor < 1 || flor > 7) System.out.println("Таких этажей нет");
                    else {
                        queue.add(flor);
                        executor.submit(() -> {
                            if (queue.isEmpty()) {
                                System.out.println("Жду команду");
                            } else {
                                move.set(queue.peek());
                                if (move.get() < current) down(move.get());
                                else up(move.get());
                            }
                        });
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Введите корректную команду");
                }
            }
        }

    }
}
