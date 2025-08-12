package model;

/**
 * Ramadan Masadekh
 * Represents a gym membership for a user.
 * 
**/
public class Membership {
    private int membershipId;
    private int memberId;      
    private String type;
    private String description;
    private double price;
    private int durationMonths;
    private String startDate;    
    private String endDate;      

    public Membership() {
    }

    public Membership(int membershipId, int memberId, String type, String description,
                      double price, int durationMonths, String startDate, String endDate) {
        this.membershipId = membershipId;
        this.memberId = memberId;
        this.type = type;
        this.description = description;
        this.price = price;
        this.durationMonths = durationMonths;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // getters and setters
    public int getMembershipId() { return membershipId; }
    public void setMembershipId(int membershipId) { this.membershipId = membershipId; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getDurationMonths() { return durationMonths; }
    public void setDurationMonths(int durationMonths) { this.durationMonths = durationMonths; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    @Override
    public String toString() {
        return "Membership{id=" + membershipId + ", memberId=" + memberId +
               ", type='" + type + "', price=" + price + ", months=" + durationMonths + "}";
    }
}