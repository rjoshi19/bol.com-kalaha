package com.bol.assignment.web.service.function;

import com.bol.assignment.web.exception.KalahaIllegalMoveException;

import java.util.Objects;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface CheckedBiConsumer<T, U> extends BiConsumer<T, U>{
   void accept(T t, U u) throws KalahaIllegalMoveException;
   default CheckedBiConsumer<T, U> andThen(CheckedBiConsumer<? super T, ? super U> after) {
      Objects.requireNonNull(after);

      return (l, r) -> {
         accept(l, r);
         after.accept(l, r);
      };
   }
}