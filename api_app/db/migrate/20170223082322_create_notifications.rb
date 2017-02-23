class CreateNotifications < ActiveRecord::Migration[5.0]
  def change
    create_table :notifications do |t|
      t.references :user, index: true
      t.references :notified_by, index: true
      t.references :posting, index: true
      t.references :identifier, index: true
      t.string :notice_type
      t.boolean :read, default: false

      t.timestamps null: false
    end
    add_foreign_key :notifications, :users
    add_foreign_key :notifications, :users, column: :notified_by_id
    add_foreign_key :notifications, :postings
  end
end
