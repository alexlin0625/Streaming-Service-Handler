package Application;
import java.util.UUID;
import java.time.LocalDateTime;

public abstract class Transactions {
    private UUID transactionId;
    private LocalDateTime date;
    private int price; // license fee

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId() {
        this.transactionId = UUID.randomUUID();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate() {
        this.date = LocalDateTime.now();
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}