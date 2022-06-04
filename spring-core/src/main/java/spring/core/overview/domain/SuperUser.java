package spring.core.overview.domain;

import lombok.Data;
import spring.core.overview.annotation.Super;

@Data
@Super
public class SuperUser extends User{
    private String address;
}
