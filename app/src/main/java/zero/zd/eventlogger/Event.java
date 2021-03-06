package zero.zd.eventlogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Event {

    private final UUID mId;
    private String mEvent;
    private Date mDate;

    public Event() {
        this("", new Date(), UUID.randomUUID());
    }

    public Event(String event, Date date, UUID uuid) {
        mId = uuid;
        mEvent = event;
        mDate = date;
    }

    public UUID getId() {
        return mId;
    }

    public String getEvent() {
        return mEvent;
    }

    public void setEvent(String event) {
        mEvent = event;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getStringDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, MMMM dd, yyyy", Locale.ENGLISH);
        return simpleDateFormat.format(mDate);
    }

    public String getStringTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        return simpleDateFormat.format(mDate);
    }
}
