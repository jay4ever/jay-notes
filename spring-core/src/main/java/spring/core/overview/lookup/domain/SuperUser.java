package spring.core.overview.lookup.domain;

import lombok.Data;
import spring.core.overview.lookup.annotation.Super;

@Data
@Super
public class SuperUser extends User{
    private String address;
}
