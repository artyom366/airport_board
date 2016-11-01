
package lv.airport.board.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "flight"
})
@XmlRootElement(name = "data")
public class Data {

    protected List<Data.Flight> flight;

    public List<Data.Flight> getFlights() {
        if (flight == null) {
            flight = new ArrayList<Data.Flight>();
        }
        return this.flight;
    }

    public void setFlights(final List<Data.Flight> flights) {
        flight = new ArrayList<>(flights);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "id",
        "code",
        "number",
        "from",
        "timeFrom",
        "to",
        "timeTo",
        "status",
        "delay"
    })
    public static class Flight {

        @XmlElement(required = true)
        protected String id;
        @XmlElement(required = true)
        protected String code;
        @XmlElement(required = true)
        protected String number;
        @XmlElement(required = true)
        protected String from;
        @XmlElement(name = "time_from", required = true)
        protected String timeFrom;
        @XmlElement(required = true)
        protected String to;
        @XmlElement(name = "time_to", required = true)
        protected String timeTo;
        @XmlElement(required = true)
        protected String status;
        @XmlElement(required = true)
        protected String delay;

        public String getId() {
            return id;
        }

        public void setId(String value) {
            this.id = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String value) {
            this.number = value;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String value) {
            this.from = value;
        }

        public String getTimeFrom() {
            return timeFrom;
        }

        public void setTimeFrom(String value) {
            this.timeFrom = value;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String value) {
            this.to = value;
        }

        public String getTimeTo() {
            return timeTo;
        }

        public void setTimeTo(String value) {
            this.timeTo = value;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String value) {
            this.status = value;
        }

        public String getDelay() {
            return delay;
        }

        public void setDelay(String value) {
            this.delay = value;
        }
    }
}
