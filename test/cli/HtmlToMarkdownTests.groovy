/**
 * Test case for the "html-to-markdown" Griffon command.
 */

import griffon.test.AbstractCliTestCase

class HtmlToMarkdownTests extends AbstractCliTestCase {
    void testDefault() {
        execute(["html-to-markdown"])

        assertEquals 0, waitForProcess()
        verifyHeader()

        // Make sure that the script was found.
        assertFalse "HtmlToMarkdown script not found.", output.contains("Script not found:")
    }
}
