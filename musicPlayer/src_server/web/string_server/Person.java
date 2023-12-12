package web.string_server;


import web.WebController;
import web.WebInterface;

class Person implements WebInterface {

    int ownID;
    String name;
    WebController controller;

    public Person(String name) {
        this.name = name;
        controller = new WebController(this);
    }

    @Override
    public void webAction(String message) {
        showMessage(message.charAt(0), message.substring(1));
    }

    private void send(int targetID, String info) {
        controller.send(targetID + "#" + info);
    }

    private void showMessage(char index, String msg) {
        switch (index) {
            case 'a':
                ownID = Integer.parseInt(msg);
                break;
            case 'b':
                System.out.println(name + "Re: " + msg);
                break;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Person mike = new Person("Mike");

        Thread.sleep(50);

        Person gan_yu = new Person("Gan Yu");

        Thread.sleep(100);

        mike.send(102, "So beautiful! ");

        Thread.sleep(100);

        gan_yu.send(101, "Thk, Mike.");

        Thread.sleep(100);

        gan_yu.send(101, "Dadada");
    }
}
