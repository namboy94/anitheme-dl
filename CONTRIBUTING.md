# Contributing to themes.moe-dl

## Copyright and Management

The original author, Hermann Krumrey, has the absolute authority on the management
of this project and may steer the development process as he sees fit.

Contributions will be attributed to the author of said code and the copyright will
remain the author's.

## Coding guidelines

**Unit Testing**

Code should be unit tested with a near-100% coverage. This may not always be completely
feasible, but at least 90%+ should be targeted. However, Unit tests should not just be
done to achieve a high test coverage, writing useful tests is even more important,
just not as easily measurable.

**Style**

We feel that a unified coding style is important, which is why we require a linter to
be used. In this case **KtLint** is used. Code must pass KtLint's tests.

Additionally, the following coding conventions should be upkept:

Brackets are placed like this:

    if (condition) {
        ...
    }

Indentation is done using 4 spaces.

Indentation is important. Always indent as if you were writing python code.

**Documentation**

We use Dokka to create automated documentation from KDoc comments. As a result, all
classes, methods and class/instance variables should be decribed using KDoc comments.

Hard to understand parts of code within a method should always be commented
accordingly.