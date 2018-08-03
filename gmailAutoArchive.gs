function gmailAutoArchiver() {
  
  // auto runs every 12 hours (requires Trigger to be set up)
  // To be run through google scripts. Requires Gmail Permissions
  
  // Original version: https://gist.github.com/anonymous/2cca33d376f7f924fdaa67891ad098cc
  // helpful page for syntax:  https://developers.google.com/apps-script/reference/gmail/gmail-app
  
  var delayDays = 5; // will only impact emails more than 5 days old
  var maxDate = new Date();
  maxDate.setDate(maxDate.getDate()-delayDays); // what was the date at that time?

  // Get all the threads in the Inbox regardless of label, up to 500 at a time
  var threads = GmailApp.getInboxThreads(0,500)
  
  // Archive all the threads if they're read AND older than the limit we set in delayDays 
  // Messages with a star applied are ignored
  for (var i = 0; i < threads.length; i++) {
    if ((threads[i].getLastMessageDate()<maxDate) && !threads[i].isUnread() && !threads[i].hasStarredMessages())
    {
      
      threads[i].moveToArchive();
    }
  }
}
