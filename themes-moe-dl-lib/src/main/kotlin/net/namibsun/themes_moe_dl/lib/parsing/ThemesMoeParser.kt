package net.namibsun.themes_moe_dl.lib.parsing

/**
 * ThemesMoeParser is a class that parses https://themes.moe.
 *
 * The parser can be configured in a variety of ways to deliver different results
 * using a variety of optional parameters, which all default to true
 *
 * For example, the class may be called with a simple
 *
 *     ThemesMoeParser(),
 *
 * but say one wants to only include series that are currently being watched:
 *
 *     ThemesMoeParser(includeCompleted = false,
 *                     includeOnHold = false,
 *                     includeDropped = false,
 *                     includeOp = false,
 *                     includeEd = false,
 *                     includeDuplicates = false)
 *
 * or say one wants to fetch all series but exclude the Ending themes, which would result in the call
 *
 *     ThemesMoeParser(includeEd = false)
 *
 * @param includeCompleted Specifies if completed series are fetched, if applicable
 * @param includeCurrentlyWatching Specifies if series currently being watched is fetched, if applicable
 * @param includeDropped Specifies if dropped series are fetched, if applicable
 * @param includeOnHold Specified if series that are on hold should be fetched, if applicable
 * @param includeOp Specifies if opening themes should be included in the result
 * @param includeEd Speciifes if the ending themes should be included in the result
 */
class ThemesMoeParser constructor(includeCompleted: Boolean = true,
                                  includeCurrentlyWatching: Boolean = true,
                                  includeOnHold: Boolean = true,
                                  includeDropped: Boolean = true,
                                  includeOp: Boolean = true,
                                  includeEd: Boolean = true,
                                  includeDuplicates: Boolean = true) {
    private val includeCompleted = includeCompleted
    private val includeCurrentlyWatching = includeCurrentlyWatching
    private val includeOnHold = includeOnHold
    private val includeDropped = includeDropped
    private val includeOp = includeOp
    private val includeEd = includeEd
    private val includeDuplicates = includeDuplicates
}
