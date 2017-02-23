# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20170223092715) do

  create_table "categories", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.string   "category_name"
    t.datetime "created_at",                null: false
    t.datetime "updated_at",                null: false
    t.integer  "active_flg",    default: 1
  end

  create_table "comments", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.integer  "user_id"
    t.integer  "posting_id"
    t.string   "content"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["posting_id"], name: "index_comments_on_posting_id", using: :btree
    t.index ["user_id"], name: "index_comments_on_user_id", using: :btree
  end

  create_table "likes", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.integer  "user_id"
    t.integer  "posting_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["posting_id"], name: "index_likes_on_posting_id", using: :btree
    t.index ["user_id"], name: "index_likes_on_user_id", using: :btree
  end

  create_table "notifications", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.integer  "user_id"
    t.integer  "notified_by_id"
    t.integer  "posting_id"
    t.integer  "comment_id"
    t.string   "notice_type"
    t.boolean  "read",           default: false
    t.datetime "created_at",                     null: false
    t.datetime "updated_at",                     null: false
    t.index ["comment_id"], name: "fk_rails_9268535f02", using: :btree
    t.index ["notified_by_id"], name: "index_notifications_on_notified_by_id", using: :btree
    t.index ["posting_id"], name: "index_notifications_on_posting_id", using: :btree
    t.index ["user_id"], name: "index_notifications_on_user_id", using: :btree
  end

  create_table "postings", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.string   "user_id",                                                              null: false
    t.binary   "image",          limit: 16777215
    t.string   "content"
    t.decimal  "latitude",                        precision: 11, scale: 8
    t.decimal  "longitude",                       precision: 11, scale: 8
    t.string   "address"
    t.string   "place_name"
    t.datetime "created_at",                                                           null: false
    t.datetime "updated_at",                                                           null: false
    t.string   "category_id"
    t.integer  "active_flg",                                               default: 1
    t.integer  "likes_count",                                              default: 0
    t.string   "place_category"
  end

  create_table "reactions", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.string   "posting_id"
    t.string   "user_id"
    t.integer  "like"
    t.string   "comment"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "relationships", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.integer  "follower_id"
    t.integer  "followed_id"
    t.datetime "created_at",  null: false
    t.datetime "updated_at",  null: false
    t.index ["followed_id"], name: "index_relationships_on_followed_id", using: :btree
    t.index ["follower_id", "followed_id"], name: "index_relationships_on_follower_id_and_followed_id", unique: true, using: :btree
    t.index ["follower_id"], name: "index_relationships_on_follower_id", using: :btree
  end

  create_table "users", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
    t.string   "user_id"
    t.string   "email"
    t.string   "password_digest"
    t.binary   "icon",              limit: 4294967295
    t.string   "icon_content_type"
    t.string   "fb_account",                           default: "0"
    t.datetime "created_at",                                         null: false
    t.datetime "updated_at",                                         null: false
    t.string   "auth_token"
    t.integer  "active_flg",                           default: 1
    t.index ["user_id"], name: "index_users_on_user_id", using: :btree
  end

  add_foreign_key "comments", "postings"
  add_foreign_key "comments", "users"
  add_foreign_key "likes", "postings"
  add_foreign_key "likes", "users"
  add_foreign_key "notifications", "comments"
  add_foreign_key "notifications", "postings"
  add_foreign_key "notifications", "users"
  add_foreign_key "notifications", "users", column: "notified_by_id"
end
