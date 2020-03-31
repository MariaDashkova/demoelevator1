package com.testtask.demoelevator1;

import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.*;

@Service
public class SimpleElevatorService {

    private static final int SPEED = 10000;
    LinkedList<Date> time = new LinkedList<>();
    LinkedList<Integer> queueFloor = new LinkedList<>();

    public void print() {
        for (Date d : time
        ) {
            System.out.println(d + " " + queueFloor.get(time.indexOf(d)));
        }
    }

    public Date currentFlor(Date current) {

        Date k = null;
        long min = current.getTime();
        for (Date date : time
        ) {
            if ((date.getTime() - current.getTime()) >= 0 && (date.getTime() - current.getTime()) < min) {
                min = Math.abs(current.getTime() - date.getTime());

                k = date;
            }
        }

        return k;
    }


    public void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("Лифт в работае, для того чтобы поехать на нужный этаж - введите номер этажа" +
                " \n чтобы вывести текущий этаж - введите 0 ");
        System.out.print("Сейчас лифт на 7 этаже.Введите этаж: ");
        String str;
        Date dateNow = null;
        int floor, currentfloor = 7;
        while (true) {
            str = in.nextLine();
            if (str.equals("p")) print();
            else {
                try {
                    floor = Integer.parseInt(str);
                    dateNow = new Date();
                    if (floor == 0) {
                        Date curr = currentFlor(dateNow);
                        //ближайший к времени этаж
                        int cur = queueFloor.get(time.indexOf(curr));
                        int id = time.indexOf(curr);
                        //разница в секундах
                        long dist = Math.abs(curr.getTime() - dateNow.getTime()) / SPEED;
                        if (id == 0) {
                            System.out.println(cur + dist);
                        } else {
                            if (cur - queueFloor.get(id - 1) < 0) System.out.println(cur + dist);
                            else System.out.println(cur - dist);
                        }

                    } else if (floor < 8 && floor >0){

                        if (time.isEmpty()) {

                            queueFloor.add(floor);
                            dateNow.setTime(dateNow.getTime() + SPEED * (currentfloor - floor));
                            time.add(dateNow);


                        } else {
                            // лифт едет сначала вниз потом наверх
                            currentfloor = queueFloor.getLast();

                            if (currentfloor < floor) {
                                int i = queueFloor.size() - 1;
                                while (i >= 0 && queueFloor.get(i) < floor) {
                                    i--;
                                }
                                if (i < 0) i = queueFloor.size() - 1;
                                long prom = 4000 + Math.abs((queueFloor.get(i) - floor) * SPEED);
                                dateNow.setTime(time.get(i).getTime() + prom);
                                time.add(i + 1, dateNow);
                                queueFloor.add(i + 1, floor);

                                if (i != 0) {
                                    for (int j = i; j < queueFloor.size(); j++) {
                                        time.get(j).setTime(time.get(j).getTime() + prom);
                                    }
                                }

                            } else {
                                dateNow.setTime(time.getLast().getTime() + 4000 + (queueFloor.getLast() - floor) * SPEED);
                                time.add(dateNow);
                                queueFloor.add(floor);
                            }

                        }

                    }else System.out.println("Существет только 7 этажей");
                } catch (NumberFormatException e) {
                    System.out.println("Введите корректную команду");
                }
            }
        }
    }

}
