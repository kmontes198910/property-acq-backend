package com.kynsof.identity.application.command.auth.autenticate;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
public class DeleteRequest  {
   public String username;
   public String password;
}
