class ChangeColumnNameOfNotifications < ActiveRecord::Migration[5.0]
  def change
    rename_column :notifications, :identifier, :comment_id
    add_foreign_key :notifications, :comments
  end
end
