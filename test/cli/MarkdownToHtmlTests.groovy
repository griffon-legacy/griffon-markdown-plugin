/**
 * Test case for the "markdown-to-html" Griffon command.
 */

import griffon.test.AbstractCliTestCase

class MarkdownToHtmlTests extends AbstractCliTestCase {
    void testDefault() {
        execute(["markdown-to-html"])

        assertEquals 0, waitForProcess()
        verifyHeader()

        // Make sure that the script was found.
        assertFalse "MarkdownToHtml script not found.", output.contains("Script not found:")
    }
}
