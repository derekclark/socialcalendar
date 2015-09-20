package uk.co.socialcalendar.message;

public class Message {
    String text;

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        Message message = (Message) obj;

        return true;
    }

    public int hashcode(){
        int hash = 7;
        hash = 31;
        return hash;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
