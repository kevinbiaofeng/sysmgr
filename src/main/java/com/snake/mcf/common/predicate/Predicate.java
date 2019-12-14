package com.snake.mcf.common.predicate;

public interface Predicate<T> {

    /**
     * 根据传入对象判定结果
     *
     * @param t
     * @return
     */
    boolean eval(T t);
    
    
}
