// START OF CHANGE 6
package com.piisw.jpa.dto;

import java.time.LocalDateTime;

public record EventFollowerDTO (String description,
                                LocalDateTime time,
                                boolean analysisRequired,
                                String content,
                                LocalDateTime subscriptionDate) {
}
// END OF CHANGE 6
