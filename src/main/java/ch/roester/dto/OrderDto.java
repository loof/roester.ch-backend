package ch.roester.dto;

import ch.roester.app_user.AppUser;

@ApiModel()
public class OrderDto extends AbstractDto<Integer> {
    private Integer id;
    private AppUser appUser;
    private List<Position> positions;
    private Instant lastUpdatedOn;
    private Instant createdOn;

    public OrderDto() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setPositions(java.util.List<ch.roester.order.Position> positions) {
        this.positions = positions;
    }

    public java.util.List<ch.roester.order.Position> getPositions() {
        return this.positions;
    }

    public void setLastUpdatedOn(java.time.Instant lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public java.time.Instant getLastUpdatedOn() {
        return this.lastUpdatedOn;
    }

    public void setCreatedOn(java.time.Instant createdOn) {
        this.createdOn = createdOn;
    }

    public java.time.Instant getCreatedOn() {
        return this.createdOn;
    }
}