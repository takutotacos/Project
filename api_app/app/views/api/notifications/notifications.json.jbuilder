json.notifications @notifications do |notification|
  json.id notification.id
  json.notified_by_id notification.notified_by_id
  json.posting_id notification.posting.id
  json.comment_id notification.comment_id
  json.notice_type notification.notice_type
  json.read notification.read
  json.created_at notification.created_at
  json.notified_by do
    json.id notification.notified_by.id
    json.user_id notification.notified_by.user_id
  end
  if notification.comment.present?
    json.comment do
      json.id notification.comment.id
      json.content notification.comment.content
    end
  end
  json.posting do
    json.id notification.posting.id
    json.content notification.posting.content
    json.like_counts notification.posting.likes_count
  end
end
