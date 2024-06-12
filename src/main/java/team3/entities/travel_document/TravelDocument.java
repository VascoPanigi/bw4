package team3.entities.travel_document;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "document_type")
public abstract class TravelDocument {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate issueDate;

    public TravelDocument(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public TravelDocument() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
}
