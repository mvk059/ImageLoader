package com.mvk.imageloader.data.either

/**
 * The [Either] type represents values with two possibilities. A value of type [Either] is either
 * [Either.Left] or [Either.Right], where both types are object wrappers with generic type parameters.
 *
 * The [Either] type is mostly used to represent a value which is either correct or an error;
 * by convention, the [Left] type is used to hold an error value and the [Right] is used to hold a
 * correct value. Mnemonic: "right" also means "correct".
 */
sealed class Either<out L, out R> {

    class Left<T>(
        val value: T,
    ) : Either<T, Nothing>()

    class Right<T>(
        val value: T,
    ) : Either<Nothing, T>()

    fun rightOrNull(): R? = when (this) {
        is Left -> null
        is Right -> this.value
    }
}

/**
 * Short-hand method to wrap the receiver object with [Either.Left]
 */
fun <T> T.left(): Either<T, Nothing> = Either.Left(this)

/**
 * Short-hand method to wrap the receiver object with [Either.Right]
 */
fun <T> T.right(): Either<Nothing, T> = Either.Right(this)
