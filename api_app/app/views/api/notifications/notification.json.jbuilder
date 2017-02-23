json.id @notification.id
json.notified_by_id @notification.notified_by_id
json.posting_id @notification.posting.id
json.comment_id @notification.comment_id
json.notice_type @notification.notice_type
json.read @notification.read
json.notified_by do
  json.id @notification.notified_by.id
  json.user_id @notification.notified_by.user_id
end
json.comment do
  json.id @notification.comment.id
  json.content @notification.comment.content
end
json.posting do
  json.id @notification.posting.id
  json.content @notification.posting.content
  json.like_counts @notification.posting.like_counts
end
